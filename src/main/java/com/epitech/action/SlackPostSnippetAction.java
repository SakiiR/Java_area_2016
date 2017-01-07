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

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Created by terrea_l on 07/01/17.
 */
public class SlackPostSnippetAction implements IAction {

    private String token;
    private Object data;
    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    public SlackPostSnippetAction(String token) {
        this.token = token;
        data = null;
    }

    public static class SnippetObject {

        String content;
        String type;
        String title;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            if (content != null) {
                this.content = content;
            } else {
                this.content = "";
            }
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            if (type != null) {
                this.type = type;
            } else {
                this.type = "text";
            }
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            if (title != null) {
                this.title = title;
            } else {
                this.title = "";
            }
        }
    }

    @Override
    public ErrorCode run() {
        List<SnippetObject> codes = new ArrayList<>();
        SnippetObject code;
        String text;
        URL url;
        InputStream in;
        try {
            SlackWebApiClient client = SlackClientFactory.createWebApiClient(this.token);
            FileList files = client.getFileList();
            for (File file : files.getFiles()) {
                java.util.Date date = new java.util.Date(file.getCreated()*1000);
                if (Objects.equals(file.getMode(), "snippet") && AreaWorker.isNewEntity(date)) {
                    url = new URL(file.getUrl_private_download());
                    in = url.openStream();
                    code = new SnippetObject();
                    text = org.apache.commons.io.IOUtils.toString(in);
                    code.setContent(text);
                    code.setTitle(file.getTitle());
                    code.setType(file.getFiletype());
                    codes.add(code);
                    in.close();
                }
            }
        } catch (Exception e) {
            Logger.logError("Slack: " + e.toString());
            return ErrorCode.AUTH;
        }
        if (codes.size() == 0) {
            data = null;
        } else {
            data = codes;
        }
        return ErrorCode.SUCCESS;
    }

    @Override
    public Object getData() {
        return data;
    }
}
