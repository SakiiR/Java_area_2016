package com.epitech.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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

    /**
     * the constructor for Notification
     */
    public                  Notification() {
        this.created = new Date();
    }

    /**
     * the getter for the id
     * @return a string id
     */
    public String           getId() {
        return id;
    }

    /**
     * the setter for the id
     * @param id the id to set
     * @return a Notification this
     */
    public Notification     setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * the getter for the user
     * @return a User
     */
    public User             getUser() {
        return user;
    }

    /**
     * the setter for the user
     * @param user the user to set
     * @return a Notification this
     */
    public Notification     setUser(User user) {
        this.user = user;
        return this;
    }

    /**
     * the getter for the message
     * @return a string message
     */
    public String           getMessage() {
        return message;
    }

    /**
     * the setter for the message
     * @param message the message to set.
     * @return a Notification this
     */
    public Notification     setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * the getter for the created date
     * @return a Date
     */
    public Date             getCreated() {
        return created;
    }

    /**
     * the setter for the created date
     * @param created the created date to set.
     * @return a Notification this
     */
    public Notification     setCreated(Date created) {
        this.created = created;
        return this;
    }

    /**
     * this function return a boolean false is the
     * notification is unread, true if it is
     * @return boolean isReaded
     */
    public boolean          isReaded() {
        return readed;
    }

    /**
     * set the readed message to true or false
     * @param readed the readed state to set.
     * @return a Notification this
     */
    public Notification     setReaded(boolean readed) {
        this.readed = readed;
        return this;
    }

    /**
     * this function returns all the information
     * about a notification
     * @return a string information
     */
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
