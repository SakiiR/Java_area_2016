package com.epitech.action;

import com.dropbox.core.v1.DbxEntry;
import com.epitech.utils.ErrorCode;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import com.epitech.utils.Logger;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jdk.nashorn.internal.parser.JSONParser;

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
        Logger.logInfo("inside Drop");

        try {
            ListFolderResult result = client.files().listFolder("");
            while (true) {
                Logger.logInfo("inside boucle");
                for (Metadata metadata : result.getEntries()) {
                    System.out.println(metadata.getPathLower());
                    Metadata bla = client.files().getMetadata(metadata.getPathLower());
                    Logger.logInfo(" bla = %s", bla.toStringMultiline());
                   /* JSONParser parser = new JSONParser();
                    JSONPObject json = (JSONPObject) parser.parse(bla.toString());
                    JSONPObject jsonpObject = new JSONPObject(bla.toString());
*/

                }
                if (!result.getHasMore()) {
                    break;
                }
                result = client.files().listFolderContinue(result.getCursor());
            }
        } catch (DbxException e) {
            e.printStackTrace();
            Logger.logError("Error !");
            return ErrorCode.AUTH;
        }

        return ErrorCode.SUCCESS;
    }
}
