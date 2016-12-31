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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This action is triggered when a
 * new yammer message in inbox.
 */
public class                        YammerNewPrivateMessageAction implements IAction {
    private String                  token;

    /** Google HTTP Factories */
    static final HttpTransport      HTTP_TRANSPORT = new NetHttpTransport();
    static final JsonFactory        JSON_FACTORY = new JacksonFactory();

    public                          YammerNewPrivateMessageAction(String token) {
        this.token = token;
    }

    public static class             Messages {
        @Key(value = "messages")
        private List<Message>       messages;

        @Key(value = "meta")
        private MessagesMeta        meta;

        public List<Message>        getMessages() {
            return this.messages;
        }

        public MessagesMeta         getMeta() {
            return this.meta;
        }
    }

    public static class             MessagesMeta {
        @Key(value = "current_user_id")
        private long                currentUserId;

        public long                 getCurrentUserId() {
            return this.currentUserId;
        }
    }

    public static class             Message extends GenericJson {
        @Key(value = "created_at")
        private String              createdAt;

        @Key(value = "sender_id")
        private long                senderId;

        @Key(value = "body")
        private MessageBody         body;

        public MessageBody          getBody() {
            return this.body;
        }

        public String               getCreatedAt() {
            return this.createdAt;
        }

        public long                 getSenderId() {
            return this.senderId;
        }
    }

    public static class             MessageBody {
        @Key(value = "parsed")
        private String              parsed;

        @Key(value = "plain")
        private String              plain;

        @Key(value = "rich")
        private String              rich;

        public String               getParsed() {
            return this.parsed;
        }

        public String               getPlain() {
            return this.plain;
        }

        public String               getRich() {
            return this.rich;
        }
    }

    public ErrorCode                run() {
        Logger.logSuccess("run() YammerNewPrivateMessageAction %s", this.token);

        HttpRequestFactory          requestFactory = this.HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest) throws IOException {
                httpRequest.setParser(new JsonObjectParser(JSON_FACTORY));
            }
        });
        try {
            HttpRequest                 request = requestFactory.buildGetRequest(new GenericUrl("https://www.yammer.com/api/v1/messages/private.json"));

            request.getHeaders().setAuthorization(String.format("Bearer %s", this.token));
            Messages messageFeed = request.execute().parseAs(Messages.class);
            List<Message>               newMessages = new ArrayList<>();
            for (Message m : messageFeed.getMessages()) {
                if (m.getSenderId() != messageFeed.getMeta().getCurrentUserId()) {
                    // format and check date
                    Logger.logInfo("Date : %s", m.getCreatedAt());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorCode.SUCCESS;
    }

    public Object                   getData() {
        return null;
    }
}
