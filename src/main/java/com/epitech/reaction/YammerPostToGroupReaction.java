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

/**
 * this class is a reaction posting to a yammer group from an action
 */
public class YammerPostToGroupReaction implements IReaction {
    private String                          token;

    /** Google HTTP Factories */
    static final HttpTransport              HTTP_TRANSPORT = new NetHttpTransport();
    static final JsonFactory                JSON_FACTORY = new JacksonFactory();

    /**
     * YammerPostToGroupReaction constructor
     * @param token the userId for authentification
     */
    public YammerPostToGroupReaction(String token) { this.token = token; }

    /**
     * Group class for parsing json response
     *
     */
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

    /**
     * PostRequest class store information for sharing the @body content to @groupName group on yammer
     */
    public static class                 PostRequest {
        private String                  groupName;
        private String                  body;

        public                          PostRequest() {}

        /**
         * PostRequest constructor
         * @param groupName group to share the new post
         * @param body content of the new message post
         */
        public                          PostRequest(String groupName, String body) {
            this.groupName = groupName;
            this.body = body;
        }

        public String                   getBody() { return body; }
        public void                     setBody(String body) { this.body = body; }

        public String                   getGroupName() { return groupName; }
        public void                     setGroupName(String groupName) { this.groupName = groupName; }

    }

    /**
     * TryToPost post
     * @param postRequest new post to share on yammer
     * @param id groupId found from groupName
     * @return an ErrorCode status
     */
    private  ErrorCode                  TryToPost(PostRequest postRequest, long id) {
        HttpClient client = HttpClientBuilder.create().build();
        try {
            HttpPost req =  new HttpPost("https://www.yammer.com/api/v1/messages.json");
            StringEntity params = new StringEntity("{\"body\":\""+ postRequest.getBody() +"\",\"group_id\":\""+ id+"\"} ");
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

    /**
     * run the reaction
     * @param object a list of future new post
     * @return an ErrorCode status
     */
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
                if (post.getGroupName().length() > 0) {
                    for (Group g : groupList) {
                        if (g.getName().equals(post.getGroupName())) {
                            Logger.logSuccess("Group id found:" + g.getName() + " - " + g.getId());
                            if (TryToPost(post, g.getId()) != ErrorCode.SUCCESS) {
                                return ErrorCode.UNKNOWN;
                            }
                        }
                    }
                } else {
                    Logger.logWarning("Group not found. Sending to default group : Group id found:" + groupList.get(0).getName() + " - " + groupList.get(0).getId());
                    if (TryToPost(post, groupList.get(0).getId()) != ErrorCode.SUCCESS) {
                        return ErrorCode.UNKNOWN;
                    }
                }
            }
        } catch (Exception e) {
            Logger.logError("YammerPostToGroupReaction: " + e.toString());
            return ErrorCode.AUTH;
        }

        return ErrorCode.SUCCESS;
    }
}
