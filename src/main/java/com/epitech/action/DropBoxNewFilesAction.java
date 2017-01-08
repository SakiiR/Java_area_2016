package com.epitech.action;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.v2.files.FolderMetadata;
import com.epitech.utils.AreaFile;
import com.epitech.utils.ErrorCode;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.epitech.utils.Logger;
import com.epitech.worker.AreaWorker;

import java.io.*;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This action class get all the new files
 * on DropBox
 */
public class                            DropBoxNewFilesAction implements IAction {
    private String                      token;

    private Object                      data = null;

    /**
     * The constructor for DropBoxNewFilesAction
     * @param token the token from the oauth2 Connexion
     */
    public                              DropBoxNewFilesAction(String token) {
        this.token = token;
    }

    /**
     * The getter for the Object data containing a list
     * of new files
     * @return the Object data
     */
    public Object                       getData() {
        return this.data;
    }

    /**
     * The run function called by the worker thread to
     * execute the action
     * @return an ErrorCode status
     */
    public ErrorCode                    run() {
        DbxRequestConfig                config = new DbxRequestConfig("Area_doudoune");
        DbxClientV2                     client = new DbxClientV2(config, this.token);
        List<AreaFile>                  filesData = new ArrayList<>();

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
                                    filesData.add(new AreaFile(meta.getName(), fileByteArray, mimeType));

                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Logger.logError("Errror !");
                                    return ErrorCode.AUTH;
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
