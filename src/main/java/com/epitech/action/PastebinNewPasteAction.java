package com.epitech.action;

import com.besaba.revonline.pastebinapi.Pastebin;
import com.besaba.revonline.pastebinapi.impl.factory.PastebinFactory;
import com.besaba.revonline.pastebinapi.paste.Paste;
import com.besaba.revonline.pastebinapi.response.Response;
import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;
import com.epitech.worker.AreaWorker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used to add paste on pastebin.
 */
public class PastebinNewPasteAction implements IAction {
    private String token;
    private Object data;

    /**
     * the Constructor for GmailAttachmentsAction
     * @param token the token from the oauth2 connexion
     */
    public PastebinNewPasteAction(String token) {
        this.token = token;
        data = null;
    }

    /**
     * The run function called by the worker thread
     * to execute the action
     * @return an ErrorCode status
     */
    @Override
    public ErrorCode run() {
        final List<String> keys = new ArrayList<>();
        try {
            PastebinFactory factory = new PastebinFactory();
            Pastebin pastebin = factory.createPastebin("221ed12ce0e20b3d32fed3b3545346f7");
            final Response<List<Paste>> pastesResponse = pastebin.getPastesOf(token, 15);

            if (pastesResponse.hasError()) {
                System.out.println("Impossible to get pastes ! " + pastesResponse.getError());
                data = null;
                return ErrorCode.UNKNOWN;
            }

            final List<Paste> pastes = pastesResponse.get();
            for (Paste paste : pastes) {
                Date date = new Date(paste.getPublishDate());
                if (AreaWorker.isNewEntity(date)) {
                    Logger.logSuccess("New paste created at <" + date.toString() + " with title " + paste.getKey());
                    keys.add("<www.pastebin.com/" + paste.getKey() + ">");
                } else {
                    Logger.logInfo("Checked Paste created at " + date.toString() + " with title " + paste.getKey());
                }
            }
        } catch (Exception e) {
            Logger.logError("Pastebin: " + e.toString());
            data = null;
            return ErrorCode.AUTH;
        }
        if (keys.size() == 0) {
            data = null;
        } else {
            data = keys;
        }
        return ErrorCode.SUCCESS;
    }

    /**
     * the getter for object data
     * @return an Object data
     */
    @Override
    public Object getData() {
        return data;
    }
}
