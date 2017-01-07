package com.epitech.action;

import allbegray.slack.SlackClientFactory;
import allbegray.slack.type.Channel;
import allbegray.slack.type.File;
import allbegray.slack.type.FileList;
import allbegray.slack.webapi.SlackWebApiClient;
import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;
import com.epitech.worker.AreaWorker;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.epitech.action.YammerNewPrivateMessageAction.JSON_FACTORY;

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
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }

    @Override
    public ErrorCode run() {
        List<SnippetObject> codes = new ArrayList<>();
        SnippetObject code;
        HttpRequestFactory requestFactory = this.HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest) throws IOException {
                httpRequest.setParser(new JsonObjectParser(JSON_FACTORY));
            }
        });
        try {
            SlackWebApiClient client = SlackClientFactory.createWebApiClient(this.token);
            FileList files = client.getFileList();
            for (File file : files.getFiles()) {
                java.util.Date date = new java.util.Date(file.getCreated()*1000);
                if (Objects.equals(file.getMode(), "snippet")) { //&& AreaWorker.isNewEntity(date)) {
                    code = new SnippetObject();
                    HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(file.getUrl_private_download()));
                    request.getHeaders().setAuthorization(String.format("Bearer %s", this.token));
                    request.execute();
                    code.setContent(request.toString());
                    code.setTitle(file.getTitle());
                    code.setType(file.getFiletype());
                    codes.add(code);
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
