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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by karraz_s on 04/01/17.
 */
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
            String postGroup = String.valueOf(object);
            HttpRequest request = requestFactory.buildGetRequest(new GenericUrl("https://www.yammer.com/api/v1/groups.json?mine=1"));
            request.getHeaders().setAuthorization(String.format("Bearer %s", this.token));
            String Result = request.execute().parseAsString();
            ObjectMapper mapper = new ObjectMapper();
            List<Group> groupList = mapper.readValue(Result, new TypeReference<ArrayList<Group>>() { });
            for (Group g : groupList) {
                Logger.logInfo("-> |" + g.getName() + "|" + g.getId());
                if (g.getName().equals(postGroup)) {
                    Logger.logInfo("SUCCCEEEEEEEESS");
                }
            }
        } catch (Exception e) {
            Logger.logError("YammerPostToGroupReaction: " + e.toString());
            return ErrorCode.UNKNOWN;
        }

        return ErrorCode.SUCCESS;
    }
}
