package com.epitech.action;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.v1.DbxEntry;
import com.dropbox.core.v2.files.FolderMetadata;
import com.epitech.service.GmailService;
import com.epitech.utils.ErrorCode;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import com.epitech.utils.Logger;
import com.epitech.worker.AreaWorker;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by anakin on 02/01/17.
 */
public class                            DropBoxNewFilesAction implements IAction {
    private String                      token;

    private Object                      data = null;

    public                              DropBoxNewFilesAction(String token) {
        this.token = token;
    }

    public Object                       getData() {
        return this.data;
    }

    public ErrorCode                    run() {
        DbxRequestConfig config = new DbxRequestConfig("Area_doudoune");
        DbxClientV2 client = new DbxClientV2(config, this.token);
        List<GmailService.File> filesData = new ArrayList<>();

        try {
            ListFolderResult result = client.files().listFolderBuilder("").withIncludeDeleted(false)
                    .withRecursive(true)
                    .start();
            while (true) {
                for (Metadata metadata : result.getEntries()) {
                    if (metadata instanceof FolderMetadata) {
                        Logger.logInfo("folderMetadata = %s", metadata.getPathLower());
                    } else if (metadata instanceof FileMetadata) {
                        Logger.logInfo("fileMetadata = %s", metadata.getPathLower());
                        FileMetadata meta = (FileMetadata) metadata;
                        Date date = meta.getClientModified();
                        if (date != null) {
                            if (AreaWorker.isNewEntity(date)) {
                                try {
                                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                    DbxDownloader download = client.files().download(meta.getPathLower());
                                    download.download(outputStream);
                                    String mimeType = URLConnection.guessContentTypeFromName(meta.getName());
                                    byte[] fileByteArray = outputStream.toByteArray();
                                    filesData.add(new GmailService.File(meta.getName(), fileByteArray, mimeType));

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Logger.logError("Errror !");
                                }
                            }
                        }
                    }
                }
                if (!result.getHasMore()) {
                    break;
                }
                result = client.files().listFolderContinue(result.getCursor());
            }
            this.data = filesData;
            if (filesData.size() == 0) {
                this.data = null;
            }
        } catch (DbxException e) {
            e.printStackTrace();
            Logger.logError("Error !");
            return ErrorCode.AUTH;
        }

        return ErrorCode.SUCCESS;
    }
}
