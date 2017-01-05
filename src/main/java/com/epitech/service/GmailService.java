package com.epitech.service;

import com.epitech.model.Module;
import com.epitech.utils.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.model.Message;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;


public class                GmailService implements IService {
    public                  GmailService() {}

    public String           login(String username, String password) {
        return "GMAIL_TOKEN";
    }

    public Gmail            buildGmailService(String token) {
        Gmail               service;
        GoogleCredential    credential = new GoogleCredential().setAccessToken(token);
        JsonFactory         jsonFactory = new JacksonFactory();
        HttpTransport       httpTransport = new NetHttpTransport();

        service = new Gmail.Builder(httpTransport, jsonFactory, credential).setApplicationName("GmailApi").build();
        return service;
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

        public File                     setData(byte[] data) {
            this.data = data;
            return this;
        }

        public File                     setFilename(String filename) {
            this.filename = filename;
            return this;
        }

        public File                     setMime(String mime) {
            this.mime = mime;
            return this;
        }

        public java.io.File             saveFile() throws FileNotFoundException, IOException {
            java.io.File                newFile = new java.io.File(String.format("/tmp/%s", filename));
            FileOutputStream            fileOutputStream = new FileOutputStream(newFile.getAbsoluteFile());
            fileOutputStream.write(this.data);
            fileOutputStream.close();
            return newFile;
        }
    }

    public static MimeMessage createEmail(String to,
                                          String from,
                                          String subject,
                                          String bodyText)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);
        Logger.logInfo("Creating mail with length : %d", bodyText.length());
        email.setText(bodyText);
        return email;
    }

    public static void sendMessage(Gmail service, String userId, MimeMessage email) throws IOException, MessagingException {
        Message message = GmailService.createMessageWithEmail(email);
        message = service.users().messages().send(userId, message).execute();
    }

    public static Message createMessageWithEmail(MimeMessage email)
            throws MessagingException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        email.writeTo(baos);
        String encodedEmail = Base64.encodeBase64URLSafeString(baos.toByteArray());
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

    public String                                   login(String code, Module module) {
        RestTemplate                                restTemplate = new RestTemplate();
        HttpHeaders                                 headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>>   request = new HttpEntity<>(null, headers);
        ResponseEntity<String>                      response = restTemplate.postForEntity(module.getTokenUrl() + String.format("&code=%s", code), request, String.class);

        try {
            HashMap<String, Object> json = new ObjectMapper().readValue(response.getBody(), HashMap.class);
            return (String) json.get("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}