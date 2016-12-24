package com.epitech.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * This class represent a notification
 */
public class                Notification {

    @Id
    private String          id;

    @DBRef
    private User            user;

    private String          message;

    private boolean         readed;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date            created;

    public                  Notification() {
        this.created = new Date();
    }

    public String           getId() {
        return id;
    }

    public Notification     setId(String id) {
        this.id = id;
        return this;
    }

    public User             getUser() {
        return user;
    }

    public Notification     setUser(User user) {
        this.user = user;
        return this;
    }

    public String           getMessage() {
        return message;
    }

    public Notification     setMessage(String message) {
        this.message = message;
        return this;
    }

    public Date             getCreated() {
        return created;
    }

    public Notification     setCreated(Date created) {
        this.created = created;
        return this;
    }

    public boolean          isReaded() {
        return readed;
    }

    public Notification     setReaded(boolean readed) {
        this.readed = readed;
        return this;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", message='" + message + '\'' +
                ", created=" + created +
                '}';
    }
}
