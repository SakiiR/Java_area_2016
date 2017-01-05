package com.epitech.reaction;

import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Key;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YammerPostToGroupReaction implements IReaction {
    private String                          token;

    static final HttpTransport              HTTP_TRANSPORT = new NetHttpTransport();
    static final JsonFactory                JSON_FACTORY = new JacksonFactory();

    public YammerPostToGroupReaction(String token) { this.token = token; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class                 Group extends GenericJson {
        @Key(value="group")
        private String                  type;

        @Key(value="id")
        private long                    id;

        @Key(value="full_name")
        private String                  name;

        public String                   getType() {  return type; }
        public long                     getId() { return id; }
        public String                   getName() { return name; }

    }

    public static class                 PostRequest {

        private String                  groupName;
        private String                  body;

        public PostRequest() {}

        public PostRequest(String groupName, String body) {
            this.groupName = groupName;
            this.body = body;
        }

        public String                   getBody() { return body; }
        public void                     setBody(String body) { this.body = body; }

        public String                   getGroupName() { return groupName; }
        public void                     setGroupName(String groupName) { this.groupName = groupName; }


    }

    private  ErrorCode                  TryToPost(PostRequest postRequest, long id) {
        HttpClient client = HttpClientBuilder.create().build();
        try {
            HttpPost req =  new HttpPost("https://www.yammer.com/api/v1/messages.json");
            StringEntity params =new StringEntity("{\"body\":\""+ postRequest.getBody() +"\",\"group_id\":\""+ id+"\"} ");
            req.addHeader("Authorization", "Bearer " + this.token);
            req.addHeader("content-type", "application/json");
            req.setEntity(params);
            client.execute(req);
        } catch (Exception e) {
            Logger.logError("YammerPostToGroup: " + e.toString());
            return ErrorCode.UNKNOWN;
        }
        return ErrorCode.SUCCESS;
    }

    @Override
    public ErrorCode                    run(Object object) {
        Logger.logSuccess("run() YammerPostToGroupReaction %s", this.token);

        HttpRequestFactory requestFactory = this.HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest) throws IOException {
                httpRequest.setParser(new JsonObjectParser(JSON_FACTORY));
            }
        });
        try {
            List<PostRequest> posts = (List<PostRequest>) object;
            HttpRequest request = requestFactory.buildGetRequest(new GenericUrl("https://www.yammer.com/api/v1/groups.json?mine=1"));
            request.getHeaders().setAuthorization(String.format("Bearer %s", this.token));
            String Result = request.execute().parseAsString();
            ObjectMapper mapper = new ObjectMapper();
            List<Group> groupList = mapper.readValue(Result, new TypeReference<ArrayList<Group>>() { });
            for (PostRequest post : posts) {
                for (Group g : groupList) {
                    if (g.getName().equals(post.getGroupName())) {
                        Logger.logSuccess("Group id found:" + g.getName() + " - " + g.getId());
                        if (TryToPost(post, g.getId()) != ErrorCode.SUCCESS) {
                            return ErrorCode.UNKNOWN;
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.logError("YammerPostToGroupReaction: " + e.toString());
            return ErrorCode.UNKNOWN;
        }

        return ErrorCode.SUCCESS;
    }
}
