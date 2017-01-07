package com.epitech.reaction;

import allbegray.slack.type.File;
import com.besaba.revonline.pastebinapi.Pastebin;
import com.besaba.revonline.pastebinapi.impl.factory.PastebinFactory;
import com.besaba.revonline.pastebinapi.paste.Paste;
import com.besaba.revonline.pastebinapi.paste.PasteBuilder;
import com.besaba.revonline.pastebinapi.paste.PasteVisiblity;
import com.besaba.revonline.pastebinapi.response.Response;
import com.epitech.action.SlackPostSnippetAction;
import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import java.util.Scanner;

/**
 * Created by terrea_l on 07/01/17.
 */
public class PostPasteFromFileReaction implements IReaction{

    private String token;

    public PostPasteFromFileReaction(String token) {
        this.token = token;
    }

    @Override
    public ErrorCode run(Object data) {
        List<SlackPostSnippetAction.SnippetObject> files = (List<SlackPostSnippetAction.SnippetObject>) data;
        Paste paste;
        PasteBuilder pasteBuilder;
        URL dl_url;
        Scanner s;
        Response<String> postResult;
        String text;
        String tmp = "";
        try {
            PastebinFactory factory = new PastebinFactory();
            Pastebin pastebin = factory.createPastebin("221ed12ce0e20b3d32fed3b3545346f7");
            for (SlackPostSnippetAction.SnippetObject file : files) {
                pasteBuilder = factory.createPaste();
                pasteBuilder.setRaw(file.getContent());
                pasteBuilder.setTitle(file.getTitle());
                pasteBuilder.setMachineFriendlyLanguage(file.getType());
                pasteBuilder.setVisiblity(PasteVisiblity.Public);
                paste = pasteBuilder.build();
                postResult = pastebin.post(paste, token);
                if (postResult.hasError()) {
                    Logger.logError("Creating Paste error : " + postResult.getError());
                } else {
                    Logger.logSuccess("Creating Paste at URL " + postResult.get());
                }
                Logger.logInfo("POUET");
            }
        } catch (Exception e) {

            Logger.logError("Pastebin: " + e.toString() + " : " + tmp);
            return ErrorCode.AUTH;

        }

        return ErrorCode.SUCCESS;
    }
}
