package com.epitech.reaction;

import com.epitech.service.GmailService;
import com.epitech.service.OnedriveService;
import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;
import org.nuxeo.onedrive.client.OneDriveAPI;
import org.nuxeo.onedrive.client.OneDriveBasicAPI;
import org.nuxeo.onedrive.client.OneDriveFolder;

import java.util.List;

public class OneDriveSaveFilesReaction implements IReaction {

    private String                  token;

    public OneDriveSaveFilesReaction(String token) {
        this.token = token;
    }

    public ErrorCode                run(Object data) {
        List<GmailService.File>     files = (List<GmailService.File>) data;

        Logger.logInfo("Files : %d", files.size());

        OnedriveService             onedriveService = new OnedriveService();
        return ErrorCode.SUCCESS;
    }
}
