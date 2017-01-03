package com.epitech.action;

import com.epitech.utils.ErrorCode;
import com.epitech.utils.Logger;
import com.epitech.worker.AreaWorker;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Key;
import org.springframework.format.annotation.DateTimeFormat;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by segmev on 02/01/17.
 */
public class                            FacebookNewPostsAction implements IAction {
    private String                      token;

    /** Google HTTP Factories */
    static final HttpTransport          HTTP_TRANSPORT = new NetHttpTransport();
    static final JsonFactory            JSON_FACTORY = new JacksonFactory();

    private Object                      data = null;

    public                              FacebookNewPostsAction(String token) {
        this.token = token;
    }
    public static class             Posts {
        @Key(value = "data")
        private List<Post>          postList;

        public List<Post>           getPosts() {
            return this.postList;
        }
    }

    public static class             Post extends GenericJson {
        @Key(value = "story")
        private String              story;

        @Key(value = "created_time")
        private String              createdAt;

        public String               getStory() { return this.story; }

        public String               getCreatedAt() { return this.createdAt; }
    }

    private Date                    formatDate(String stringDate) {
                                                                      //2015-11-19T14:25:21+0000
        DateFormat                  dateFormat = new SimpleDateFormat("yyyy-MM-ddTkk:mm:ss+Z");
        Date                        date = null;
        try {
            date = dateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Object                       getData() {
        return this.data;
    }

    public ErrorCode                    run() {
        String getPostsRoad = java.text.MessageFormat.format("https://graph.facebook.com/v2.8/me?fields=posts&access_token={0}", token);
//        return ErrorCode.SUCCESS;

        Logger.logSuccess("run() FacebookNewPostsAction %s", this.token);

        HttpRequestFactory          requestFactory = this.HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest) throws IOException {
                httpRequest.setParser(new JsonObjectParser(JSON_FACTORY));
            }
        });
        try {
            HttpRequest                 request = requestFactory.buildGetRequest(new GenericUrl(getPostsRoad));

            //request.getHeaders().setAuthorization(String.format("Bearer %s", this.token));
            Posts postsFeed = request.execute().parseAs(Posts.class);
            List<Post> newPosts = new ArrayList<>();
            for (Post p : postsFeed.getPosts()) {
                Logger.logSuccess(p.getCreatedAt());
                Date d = this.formatDate(p.getCreatedAt());
                if (AreaWorker.isNewEntity(d)) {
                    newPosts.add(p);
                }
            }
            Logger.logSuccess("There is %d new Facebook Posts !!", newPosts.size());
            this.data = newPosts;
            if (newPosts.size() == 0) this.data = null;
        } catch (Exception e) {
            e.printStackTrace();
            return ErrorCode.AUTH;
        }
        return ErrorCode.SUCCESS;
        //return ErrorCode.SUCCESS;
    }
}
