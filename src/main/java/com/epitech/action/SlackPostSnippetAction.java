package com.epitech.action;

import allbegray.slack.SlackClientFactory;
import allbegray.slack.type.File;
import allbegray.slack.type.FileList;
import allbegray.slack.webapi.SlackWebApiClient;
import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;
import com.epitech.worker.AreaWorker;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.util.ArrayList;
import java.util.List;


/**
 * This class is used to know if a snippet
 * is posted on a slack channel.
 */
public class SlackPostSnippetAction implements IAction {

    private String token;
    private Object data;
    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /**
     * the Constructor for GmailAttachmentsAction
     * @param token the token from the oauth2 connexion
     */
    public SlackPostSnippetAction(String token) {
        this.token = token;
        data = null;
    }

    /**
     * This class represent a Slack Snippet.
     */
    public static class         SnippetObject {

        String content;
        String type;
        String title;

        public String           getContent() {
            return content;
        }

        public SnippetObject    setContent(String content) {
            if (content != null) {
                this.content = content;
            } else {
                this.content = "";
            }
            return this;
        }

        public String           getType() {
            return type;
        }

        public SnippetObject    setType(String type) {
            if (type != null) {
                this.type = type;
            } else {
                this.type = "text";
            }
            return this;
        }

        public String           getTitle() {
            return title;
        }

        public SnippetObject    setTitle(String title) {
            if (title != null) {
                this.title = title;
            } else {
                this.title = "";
            }
            return this;
        }
    }

    /**
     * The run function called by the worker thread
     * to execute the action
     * @return an ErrorCode status
     */
    @Override
    public ErrorCode run() {
        List<SnippetObject> codes = new ArrayList<>();
        SnippetObject code;
        String text;
        try {
            SlackWebApiClient client = SlackClientFactory.createWebApiClient(this.token);
            FileList files = client.getFileList();
            for (File file : files.getFiles()) {
                java.util.Date date = new java.util.Date(file.getCreated()*1000);
                if (AreaWorker.isNewEntity(date)) {
                    if (file.getMode().compareToIgnoreCase("snippet") == 0) {
                        code = new SnippetObject();
                        text = file.getPreview();
                        code.setContent(text);
                        code.setTitle(file.getTitle());
                        code.setType(file.getFiletype());
                        codes.add(code);
                    }
                }
            }
        } catch (Exception e) {
            Logger.logError("Slack: " + e.toString());
            data = null;
            return ErrorCode.AUTH;
        }
        if (codes.size() == 0) {
            data = null;
        } else {
            data = codes;
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
