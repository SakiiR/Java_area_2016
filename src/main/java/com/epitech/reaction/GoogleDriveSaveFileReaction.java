package com.epitech.reaction;

import com.epitech.service.DriveService;
import com.epitech.service.GmailService;
import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;

import java.util.List;

public class                                GoogleDriveSaveFileReaction implements IReaction {
    private String                          token;

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/drive-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory     DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory        JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport            HTTP_TRANSPORT;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public                                  GoogleDriveSaveFileReaction(String token) {
        this.token = token;
    }

    public ErrorCode                        run(Object object) {
        List<GmailService.File>   files = (List<GmailService.File>) object;
        DriveService                        driveService = new DriveService();
        Drive                               drive = driveService.getDriveService(this.token);


        Logger.logInfo("Reaction GoogleDriveSaveFileReaction with %d files", files.size());
        for (GmailService.File file : files) {
            try {
                java.io.File f = file.saveFile();
                com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
                fileMetaData.setName(f.getName());
                fileMetaData.setMimeType(file.getMime());
                FileContent mediaContent = new FileContent(file.getMime(), f);
                com.google.api.services.drive.model.File out = drive.files().create(fileMetaData, mediaContent).setFields("id").execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ErrorCode.SUCCESS;
    }
}
