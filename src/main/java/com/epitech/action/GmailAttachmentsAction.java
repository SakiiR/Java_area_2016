package com.epitech.action;

import com.epitech.service.GmailService;
import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.ListMessagesResponse;


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

    public                  GmailAttachmentsAction(String token) {

        this.token = token;
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
            }
        } catch (IOException e) {
            e.printStackTrace();
            Logger.logError("Error !");
        }
        return ErrorCode.SUCCESS;
    }

    private List<Message>   getNewMessages(Gmail service, String userId, List<Message> allMessages) throws IOException {
        List<Message>       messages = new ArrayList<>();
        String              date;

        for (Message m : allMessages) {
            Message message = service.users().messages().get(userId, m.getId()).execute();
            date = message.getPayload().getHeaders().get(2).getValue();
            if (this.check_date(date)){
                messages.add(message);
            }
            System.out.println(message.getPayload().getHeaders().get(2).getValue());
        }
        return messages;
    }

    private boolean         check_date(String dateString) {
        SimpleDateFormat    format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss X", Locale.ENGLISH);

        try {
            Date date = format.parse(dateString);
            Logger.logInfo("new date = %s", date.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logError("Error !");
        }
        return true;
    }

    private List<Message>       getMessagesMatchingQuery(Gmail service, String userId, String query)
            throws IOException {
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

        for (Message message : messages) {
            System.out.println(message.toPrettyString());
        }
        return messages;
    }

    public Object           getData() {
        return null;
    }
}
