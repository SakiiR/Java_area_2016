package com.epitech.action;

import com.epitech.service.GmailService;
import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;
import com.google.api.services.gmail.Gmail;

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

        Logger.logSuccess("run() GmailAttachmentsAction");
        service = gmailService.buildGmailService(this.token);

        return ErrorCode.SUCCESS;
    }

    public Object           getData() {
        return null;
    }
}
