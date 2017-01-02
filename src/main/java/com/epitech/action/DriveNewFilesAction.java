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
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class                        DriveNewFilesAction implements IAction {
    private Object                  data = null;
    private String                  token;

    public                          DriveNewFilesAction(String token) {
        this.token = token;
    }

    public ErrorCode                run() {
        DriveService                driveService = new DriveService();
        Drive                       drive = driveService.getDriveService(this.token);
        List<GmailService.File>     files = new ArrayList<>();

        String pageToken = null;
        do {
            FileList result = null;
            try {
                result = drive.files().list()
                        .setQ(String.format("modifiedTime >= '%s'", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(AreaWorker.lastCheck)))
                        .setSpaces("drive")
                        .setFields("nextPageToken, files(id, name, createdTime, mimeType)")
                        .setPageToken(pageToken)
                        .execute();
            } catch (Exception e) {
                e.printStackTrace();
                Logger.logWarning("Failed to retrieve google drive file list : %s", e.getMessage());
            }
            if (result != null) {
                for(File file: result.getFiles()) {
                    Logger.logInfo("checking %s", file.getName(), file.getCreatedTime().getValue(), new DateTime(AreaWorker.lastCheck).getValue());
                    if (!file.getMimeType().equals("application/vnd.google-apps.folder")) {
                        Logger.logInfo("Adding file %s -> %s", file.getName(), file.getMimeType());
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        boolean isOk = false;
                        try {
                            drive.files().export(file.getId(), "application/pdf").executeAndDownloadTo(outputStream);
                            isOk = true;
                        } catch (IOException e) {
                            try {
                                drive.files().get(file.getId()).executeAndDownloadTo(outputStream);
                                isOk = true;
                            } catch (IOException e1) {
                                Logger.logInfo("Failed to download file .. %s", e1.getMessage());
                            }
                        }
                        if (isOk) {
                            files.add(new GmailService.File(file.getName(), outputStream.toByteArray(), file.getMimeType()));
                        }
                    }
                }
                pageToken = result.getNextPageToken();
            }
        } while (pageToken != null);
        this.data = files;
            if (files.size() == 0) {
                this.data = null;
            }

        return ErrorCode.SUCCESS;
    }

    public Object                   getData() {
        return this.data;
    }
}
