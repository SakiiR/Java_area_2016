package com.epitech.action;

import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;

/**
 * This action recover attachments in gmail mails
 */
public class                GmailAttachmentsAction implements IAction {
    private String          token;

    public                  GmailAttachmentsAction(String token) {
        this.token = token;
    }

    public ErrorCode        run() {
        Logger.logSuccess("run() GmailAttachmentsAction");
        return ErrorCode.SUCCESS;
    }

    public Object           getData() {
        return null;
    }
}
