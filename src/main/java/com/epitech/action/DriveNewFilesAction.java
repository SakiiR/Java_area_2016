package com.epitech.action;

import com.epitech.service.DriveService;
import com.epitech.service.GmailService;
import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;
import com.epitech.worker.AreaWorker;
import com.google.api.client.util.DateTime;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import sun.rmi.runtime.Log;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class                        DriveNewFilesAction implements IAction {
    private Object                  data = null;
    private String                  token;

    public                          DriveNewFilesAction(String token) {
        this.token = token;
    }

    private Date                    getDateByDateTime(DateTime dateTime) {
        return new Date(dateTime.getValue());
    }

    public ErrorCode                run() {
        DriveService                driveService = new DriveService();
        Drive                       drive = driveService.getDriveService(this.token);
        List<GmailService.File>     files = new ArrayList<>();

        try {
            String pageToken = null;
            do {
                FileList result = drive.files().list()
                        .setQ(String.format("modifiedTime > '%s'", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(AreaWorker.lastCheck)))
                        .setSpaces("drive")
                        .setFields("nextPageToken, files(id, name, createdTime, mimeType)")
                        .setPageToken(pageToken)
                        .execute();
                for(File file: result.getFiles()) {
                    Date date = this.getDateByDateTime(file.getCreatedTime());
                    if (AreaWorker.isNewEntity(date)) {
                        Logger.logInfo("Adding file %s", file.getName());
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        drive.files().export(file.getId(), file.getMimeType()).executeMediaAndDownloadTo(outputStream);
                        files.add(new GmailService.File(file.getName(), outputStream.toByteArray(), file.getMimeType()));
                    }
                }
                pageToken = result.getNextPageToken();
            } while (pageToken != null);
            this.data = files;
            if (files.size() == 0) {
                this.data = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logWarning("Failed to retreive google drive file list : %s", e.getMessage());
        }
        return ErrorCode.SUCCESS;
    }

    public Object                   getData() {
        return this.data;
    }
}
