package com.epitech.action;

import com.epitech.service.GmailService;
import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;
import com.epitech.worker.AreaWorker;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.*;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.FileUtils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * This action recover attachments in gmail mails
 */
public class                GmailAttachmentsAction implements IAction {
    private String          token;

    private Object          data = null;

    public                  GmailAttachmentsAction(String token) {
        this.token = token;
    }

    public static class                 File {
        private byte[]                  data;
        private String                  filename;
        private String                  mime;

        public                          File(String filename, byte[] data, String mime) {
            this.data = data;
            this.filename = filename;
            this.mime = mime;
        }

        public byte[]                   getData() {
            return this.data;
        }

        public String                   getFilename() {
            return filename;
        }

        public String                   getMime() {
            return mime;
        }

        public java.io.File             saveFile() throws FileNotFoundException, IOException {
            java.io.File                newFile = new java.io.File(String.format("/tmp/%s", filename));
            FileOutputStream            fileOutputStream = new FileOutputStream(newFile.getAbsoluteFile());
            fileOutputStream.write(this.data);
            fileOutputStream.close();
            return newFile;
        }
    }

    public ErrorCode        run() {
        GmailService        gmailService = new GmailService();
        Gmail               service;
        String              query = "newer_than:1d has:attachment";
        List<Message>       messages = new ArrayList<>();

        Logger.logSuccess("run() GmailAttachmentsAction");
        service = gmailService.buildGmailService(this.token);
        try {
            messages = this.getMessagesMatchingQuery(service, "me", query);
            if (messages.size() > 0) {
                messages = this.getNewMessages(service, "me", messages);
                for (Message m : messages) {
                    this.getAttachments(service, "me", m);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Logger.logError("Error !");
            return ErrorCode.AUTH;
        }
        return ErrorCode.SUCCESS;
    }

    private  void                     getAttachments(Gmail service, String userId, Message message) throws IOException {
        List<GmailAttachmentsAction.File>   files = new ArrayList<>();
        List<MessagePart> parts = message.getPayload().getParts();
        for (MessagePart part : parts) {
            if (part.getFilename() != null && part.getFilename().length() > 0) {
                String filename = part.getFilename();
                String attId = part.getBody().getAttachmentId();
                MessagePartBody attachPart = service.users().messages().attachments().
                        get(userId, message.getId(), attId).execute();

                Base64 base64Url = new Base64(true);
                byte[] fileByteArray = base64Url.decodeBase64(attachPart.getData());
                files.add(new File(filename, fileByteArray, part.getMimeType()));
            }
        }
        this.data = files;
        if (files.size() == 0) {
            this.data = null;
        }
    }

    private List<Message>   getNewMessages(Gmail service, String userId, List<Message> allMessages) throws IOException {
        List<Message>       messages = new ArrayList<>();
        String              date;
        List<MessagePartHeader> headers;

        for (Message m : allMessages) {
            Message message = service.users().messages().get(userId, m.getId()).execute();
            headers = message.getPayload().getHeaders();
            int i = this.searchForDate(headers);
            if (i > 0) {
                date = message.getPayload().getHeaders().get(i).getValue();
                if (this.check_date(date)) {
                    messages.add(message);
                }
            }
        }
        return messages;
    }

    private int             searchForDate(List<MessagePartHeader> headers) {
        int             inc = 0;

        for (MessagePartHeader h : headers) {
            if (h.getName().equals("Date")) {
                return inc;
            }
            ++inc;
        }
        return 0;
    }

    private boolean         check_date(String dateString) {
        SimpleDateFormat    format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

        try {
            Date date = format.parse(dateString);
            if (AreaWorker.isNewEntity(date)) {
                Logger.logInfo("new date = %s", date.toString());
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logError("Error !");
        }
        return false;
    }

    private List<Message>       getMessagesMatchingQuery(Gmail service, String userId, String query) throws IOException {
        ListMessagesResponse    response = service.users().messages().list(userId).setQ(query).execute();

        List<Message> messages = new ArrayList<>();
        while (response.getMessages() != null) {
            messages.addAll(response.getMessages());
            if (response.getNextPageToken() != null) {
                String pageToken = response.getNextPageToken();
                response = service.users().messages().list(userId).setQ(query)
                        .setPageToken(pageToken).execute();
            } else {
                break;
            }
        }
        return messages;
    }

    public Object           getData() {
        return this.data;
    }
}
