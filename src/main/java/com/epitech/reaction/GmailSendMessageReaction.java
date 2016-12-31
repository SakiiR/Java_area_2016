package com.epitech.reaction;

import com.epitech.service.GmailService;
import com.epitech.utils.ErrorCode;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

public class            GmailSendMessageReaction implements IReaction {

    String              token;

    public              GmailSendMessageReaction(String token) {
        this.token = token;
    }

    public ErrorCode    run(Object data) {
        GmailService        gmailService = new GmailService();
        Gmail               service = gmailService.buildGmailService(this.token);

        try {
            GmailService.sendMessage(service, "me", GmailService.createEmail("me", "me", "AREA", (String) data));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ErrorCode.SUCCESS;
    }
}
