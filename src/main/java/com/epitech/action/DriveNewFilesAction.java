package com.epitech.action;

import com.epitech.service.DriveService;
import com.epitech.utils.AreaFile;
import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;
import com.epitech.worker.AreaWorker;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This action class get all the new files on Google Drive
 */
public class                        DriveNewFilesAction implements IAction {
    private Object                  data = null;
    private String                  token;

    /**
     * The constructor for DriveNewFilesAction
     * @param token the token from the oauth2 connexion
     */
    public                          DriveNewFilesAction(String token) {
        this.token = token;
    }

    /**
     * The run function is called by the worker thread
     * to execute the action
     * @return an ErrorCode status
     */
    public ErrorCode                run() {
        DriveService                driveService = new DriveService();
        Drive                       drive = driveService.getDriveService(this.token);
        List<AreaFile>              files = new ArrayList<>();

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
                    Logger.logInfo("checking %s", file.getName());
                    if (!file.getMimeType().equals("application/vnd.google-apps.folder")) {
                        Logger.logInfo("Adding file %s -> %s", file.getName(), file.getMimeType());
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        boolean isOk = false;
                        String extention = "";
                        try {
                            drive.files().export(file.getId(), "application/pdf").executeAndDownloadTo(outputStream);
                            extention = ".pdf";
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
                            files.add(new AreaFile(file.getName() + extention, outputStream.toByteArray(), file.getMimeType()));
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

    /**
     * A getter for the Object data with the list
     * of new files
     * @return the Object data
     */
    public Object                   getData() {
        return this.data;
    }
}
