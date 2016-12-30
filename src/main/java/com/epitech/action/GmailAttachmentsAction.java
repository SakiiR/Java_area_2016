package com.epitech.action;

import com.epitech.service.GmailService;
import com.epitech.utils.ErrorCode;

/**
 * This action recover attachments in gmail mails
 */
public class                GmailAttachmentsAction implements IAction {
    private String          token;
    private GmailService    gmailService;

    public                  GmailAttachmentsAction(String token, GmailService gmailService) {
        this.token = token;
        this.gmailService = gmailService;
    }

    public ErrorCode        run() {
        return ErrorCode.SUCCESS;
    }

    public Object           getData() {
        return null;
    }
}
