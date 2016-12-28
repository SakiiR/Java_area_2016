package com.epitech.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * This class is the representation
 * of a User.
 */
@Document(collection = "user")
public class                        User {

    @Id
    private String                  id;

    private String                  username;
    private String                  password;
    private String                  salt;

    @DBRef
    private List<Area>              areas;

    @DBRef
    private List<UserModule>        modules;

    @DBRef
    private List<Notification>      notifications;

    public                          User() {}

    public                          User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User                     setUsername(String username) {
        this.username = username;
        return this;
    }

    public String                   getUsername() {
        return this.username;
    }

    public User                     setPassword(String password) {
        this.password = password;
        return this;
    }

    public String                   getPassword() {
        return this.password;
    }

    public User                     setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public String                   getSalt() {
        return this.salt;
    }

    public List<UserModule>         getModules() {
        return modules;
    }

    public User                     setModules(List<UserModule> modules) {
        this.modules = modules;
        return this;
    }

    public User                     addModule(UserModule userModule) {
        this.modules.add(userModule);
        return this;
    }

    public User                     removeModule(UserModule userModule) {
        this.modules.remove(userModule);
        return this;
    }

    public String                   getId() {
        return id;
    }

    public User                     setId(String id) {
        this.id = id;
        return this;
    }

    public List<Notification>       getNotifications() {
        return notifications;
    }

    public User                     setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
        return this;
    }

    public User                     addNotification(Notification notification) {
        this.notifications.add(notification);
        return this;
    }

    public User                     removeNotification(Notification notification) {
        this.notifications.remove(notification);
        return this;
    }

    public List<Area>               getAreas() {
        return areas;
    }

    public User                     setAreas(List<Area> areas) {
        this.areas = areas;
        return this;
    }

    public User                     addArea(Area area) {
        this.areas.add(area);
        return this;
    }

    public User                     removeArea(Area area) {
        this.areas.remove(area);
        return this;
    }

    @Override
    public String                   toString() {
        return String.format(
                "User[id=%s, userName='%s', password='%s', salt='%s']",
                this.id,
                this.username,
                this.password,
                this.salt
        );
    }


}
