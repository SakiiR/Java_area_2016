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
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;


/**
 * This class is used to interact with gmail API.
 */
public class                                        GmailService implements IService {
    public                                          GmailService() {}

    /**
     * NOP
     *
     * @param username the username to provide to the concerned API.
     * @param password the password to provide to the concerned API.
     * @return
     */
    public String                                   login(String username, String password) {
        return "GMAIL_TOKEN";
    }

    /**
     * Get the gmail service object.
     *
     * @param token the token string.
     * @return a gmail object.
     * @see Gmail
     */
    public Gmail                                    buildGmailService(String token) {
        Gmail                                       service;
        GoogleCredential                            credential = new GoogleCredential().setAccessToken(token);
        JsonFactory                                 jsonFactory = new JacksonFactory();
        HttpTransport                               httpTransport = new NetHttpTransport();

        service = new Gmail.Builder(httpTransport, jsonFactory, credential).setApplicationName("GmailApi").build();
        return service;
    }

    /**
     * Build a gmail message to send it.
     *
     * @param to an email.
     * @param from  an email.
     * @param subject a subject.
     * @param bodyText a mail body.
     * @return a MimeMessage.
     * @throws MessagingException throw a MessagingException
     */
    public static MimeMessage                       createEmail(String to,
                                                                String from,
                                                                String subject,
                                                                String bodyText) throws MessagingException {
        Properties                                  props = new Properties();
        Session                                     session = Session.getDefaultInstance(props, null);
        MimeMessage                                 email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);
        Logger.logInfo("Creating mail with length : %d", bodyText.length());
        email.setText(bodyText);
        return email;
    }

    /**
     * Send a gmail message.
     *
     * @param service the gmail service as dependency..
     * @param userId the user id concerned. ( ex : "me" )
     * @param email the email Object
     * @throws IOException throw a IOException.
     * @throws MessagingException throw a MessagingException.
     */
    public static void                              sendMessage(Gmail service, String userId, MimeMessage email) throws IOException, MessagingException {
        Message                                     message = GmailService.createMessageWithEmail(email);

        service.users().messages().send(userId, message).execute();
    }

    /**
     * Build a message.
     *
     * @param email an email.
     * @return a google Message .
     * @throws MessagingException throw a MessagingException.
     * @throws IOException throw a IOException.
     */
    public static Message                           createMessageWithEmail(MimeMessage email) throws MessagingException, IOException {
        ByteArrayOutputStream                       baos = new ByteArrayOutputStream();

        email.writeTo(baos);
        String encodedEmail = Base64.encodeBase64URLSafeString(baos.toByteArray());
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

    /**
     * Login via the oauth code.
     *
     * @param code the code.
     * @param module the oauth module.
     * @return a string Token.
     */
    public String                                   login(String code, Module module) {
        RestTemplate                                restTemplate = new RestTemplate();
        HttpHeaders                                 headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>>   request = new HttpEntity<>(null, headers);
        ResponseEntity<String>                      response = restTemplate.postForEntity(module.getTokenUrl() + String.format("&code=%s", code), request, String.class);

        Logger.logInfo("Received from google gmail : %s", response.getBody());
        try {
            HashMap<String, Object> json = new ObjectMapper().readValue(response.getBody(), HashMap.class);
            return (String) json.get("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}