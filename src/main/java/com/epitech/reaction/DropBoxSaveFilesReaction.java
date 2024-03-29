package com.epitech.reaction;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.epitech.utils.AreaFile;
import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * this class is a reaction saving new files on DropBox
 */
public class                                DropBoxSaveFilesReaction implements IReaction{
    private String                          token;

    /**
     * the constructor for DropBoxSaveFilesReaction
     * @param token the token from the oauth2 connexion
     */
    public                                  DropBoxSaveFilesReaction(String token) {
        this.token = token;
    }

    /**
     * the function run executes the dropboxsavefile reaction
     * @param data the data to save
     * @return an ErrorCode status
     */
    public ErrorCode                        run(Object data) {
        List<AreaFile>                      files = (List<AreaFile>) data;
        DbxRequestConfig                    config = new DbxRequestConfig("area_doudoune");
        DbxClientV2                         client = new DbxClientV2(config, this.token);

        for (AreaFile file : files) {
            try {
                File savedFile = file.saveFile();
                InputStream in = new FileInputStream(savedFile.getAbsoluteFile());
                client.files().uploadBuilder("/" + file.getFilename()).uploadAndFinish(in);
                Logger.logSuccess("Uploaded %d bytes of %s", file.getData().length, file.getFilename());
                savedFile.delete();
            } catch (Exception e) {
                Logger.logWarning("Failed to upload file : %s", e.getMessage());
                return ErrorCode.AUTH;
            }
        }

        Logger.logInfo("Running DropBoxSaveFilesReaction with %d files !", files.size());
        return ErrorCode.SUCCESS;
    }
}
