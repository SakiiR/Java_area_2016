package com.epitech.action;

import com.epitech.utils.ErrorCode;

/**
 * This action is triggered when a
 * new yammer message in inbox.
 */
public class                        YammerNewPrivateMessageAction implements IAction{
    private String                  token;

    public                          YammerNewPrivateMessageAction(String token) {
        this.token = token;
    }

    public ErrorCode                run() {
        return ErrorCode.SUCCESS;
    }

    public Object                   getData() {
        return null;
    }
}
