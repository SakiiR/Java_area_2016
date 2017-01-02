package com.epitech.reaction;

import com.epitech.utils.ErrorCode;

public class                        OneDriveSaveFileReaction implements IReaction {

    private String                  token;

    public                          OneDriveSaveFileReaction(String token) {
        this.token = token;
    }

    public ErrorCode                run(Object data) {

        return ErrorCode.SUCCESS;
    }
}
