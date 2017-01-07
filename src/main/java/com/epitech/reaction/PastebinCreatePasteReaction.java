package com.epitech.reaction;

import allbegray.slack.type.File;
import com.besaba.revonline.pastebinapi.Pastebin;
import com.besaba.revonline.pastebinapi.impl.factory.PastebinFactory;
import com.besaba.revonline.pastebinapi.paste.Paste;
import com.besaba.revonline.pastebinapi.paste.PasteBuilder;
import com.besaba.revonline.pastebinapi.paste.PasteExpire;
import com.besaba.revonline.pastebinapi.paste.PasteVisiblity;
import com.besaba.revonline.pastebinapi.response.Response;
import com.epitech.action.SlackPostSnippetAction;
import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import java.util.Scanner;

/**
 * Created by terrea_l on 07/01/17.
 */
public class PastebinCreatePasteReaction implements IReaction{

    private String token;

    /**
     * the constructor for GithubCreateRepositoriesReaction
     * @param token the token from the oauth2 connexion
     */

    public PastebinCreatePasteReaction(String token) {
        this.token = token;
    }

    /**
     * this function run executes the GithubCreateRepositoriesReaction
     * @param data the data for creating new repositories
     * @return an ErrorCode status
     */

    @Override
    public ErrorCode run(Object data) {
        if (data == null) {
            return ErrorCode.SUCCESS;
        }
        List<SlackPostSnippetAction.SnippetObject> files = (List<SlackPostSnippetAction.SnippetObject>) data;
        PasteBuilder pasteBuilder;
        Paste paste;
        Response<String> postResult;
        try {
            PastebinFactory factory = new PastebinFactory();
            Pastebin pastebin = factory.createPastebin("221ed12ce0e20b3d32fed3b3545346f7");
            for (SlackPostSnippetAction.SnippetObject file : files) {
                pasteBuilder = factory.createPaste();
                pasteBuilder.setRaw(file.getContent());
                pasteBuilder.setTitle(file.getTitle());
                pasteBuilder.setMachineFriendlyLanguage("text");
                pasteBuilder.setVisiblity(PasteVisiblity.Public);
                pasteBuilder.setExpire(PasteExpire.OneWeek);
                paste = pasteBuilder.build();
                if (paste == null) {
                    Logger.logError("FAILURE");
                } else {
                    postResult = pastebin.post(paste, token);
                    if (postResult.hasError()) {
                        Logger.logError("Creating Paste error : " + postResult.getError());
                    } else {
                        Logger.logSuccess("Creating Paste at URL " + postResult.get());
                    }
                }
            }
        } catch (Exception e) {
            Logger.logError("Pastebin: " + e.toString());
            return ErrorCode.AUTH;
        }
        return ErrorCode.SUCCESS;
    }
}
