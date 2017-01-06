package com.epitech.reaction;

import com.epitech.service.GmailService;
import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;
import com.google.api.services.gmail.Gmail;

/**
 * this class is a reaction sending gmail message
 * from a action
 */
public class            GmailSendMessageReaction implements IReaction {

    String              token;

    /**
     * the constructor for GmailSendMessageReaction
     * @param token the token from the oauth2 connexion
     */
    public              GmailSendMessageReaction(String token) {
        this.token = token;
    }

    /**
     * the function run executes GmailSendMessageReaction
     * @param data the data to send gmail message
     * @return an ErrorCode status
     */
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
