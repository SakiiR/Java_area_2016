package com.epitech.reaction;

import com.epitech.service.GmailService;
import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;

import java.util.List;

public class                                DropBoxSaveFilesReaction implements IReaction{
    private String                          token;

    public                                  DropBoxSaveFilesReaction(String token) {
        this.token = token;
    }

    public ErrorCode                        run(Object data) {
        List<GmailService.File>             files = (List<GmailService.File>) data;

        Logger.logInfo("Running DropBoxSaveFilesReaction with %d files !", files.size());
        return ErrorCode.SUCCESS;
    }
}
