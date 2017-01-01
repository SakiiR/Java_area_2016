package com.epitech.reaction;

import com.epitech.service.GmailService;
import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;
import com.google.api.services.gmail.Gmail;

public class            GmailSendMessageReaction implements IReaction {

    String              token;

    public              GmailSendMessageReaction(String token) {
        this.token = token;
    }

    public ErrorCode    run(Object data) {
        GmailService        gmailService = new GmailService();
        Gmail               service = gmailService.buildGmailService(this.token);

        Logger.logInfo("Running reaction : GmailSendMessageReaction");
        try {
            GmailService.sendMessage(service, "me", GmailService.createEmail("me", "me", "AREA", (String) data));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ErrorCode.SUCCESS;
    }
}
