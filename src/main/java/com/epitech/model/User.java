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

    /**
     * the constructor for User
     */
    public                          User() {}

    /**
     * the constructor for User
     *
     * @param username the username
     * @param password the user password
     */
    public                          User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * the setter for user name
     *
     * @param username the username
     * @return the User this
     */
    public User                     setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * the getter for user name
     *
     * @return the string username
     */
    public String                   getUsername() {
        return this.username;
    }

    /**
     * the setter for the user password
     *
     * @param password the password
     * @return the User this
     */
    public User                     setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * the getter for the password
     *
     * @return the string password
     */
    public String                   getPassword() {
        return this.password;
    }

    /**
     * the setter for the salt
     *
     * @param salt the salt
     * @return the User this
     */
    public User                     setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    /**
     * the setter for the salt
     *
     * @return the string salt
     */
    public String                   getSalt() {
        return this.salt;
    }

    /**
     * the getter for modules
     *
     * @return the list of user modules
     */
    public List<UserModule>         getModules() {
        return modules;
    }

    /**
     * the setter of the modules
     *
     * @param modules a userModule list
     * @return the User this
     */
    public User                     setModules(List<UserModule> modules) {
        this.modules = modules;
        return this;
    }

    /**
     * this function add a module to a user
     *
     * @param userModule the userModule to add
     * @return the User this
     */
    public User                     addModule(UserModule userModule) {
        this.modules.add(userModule);
        return this;
    }

    /**
     * this function removes a UserModule
     *
     * @param userModule the userModule to remove
     * @return the User this
     */
    public User                     removeModule(UserModule userModule) {
        this.modules.remove(userModule);
        return this;
    }

    /**
     * the getter for the id
     *
     * @return the string id
     */
    public String                   getId() {
        return id;
    }

    /**
     * the setter for the id
     *
     * @param id the id to set
     * @return the user this
     */
    public User                     setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * the getter for notifications
     *
     * @return a list of Notifications
     */
    public List<Notification>       getNotifications() {
        return notifications;
    }

    /**
     * the setter for notifications
     *
     * @param notifications the notifications
     * @return the User this
     */
    public User                     setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
        return this;
    }

    /**
     * this function add notification
     *
     * @param notification the notification
     * @return the User this
     */
    public User                     addNotification(Notification notification) {
        this.notifications.add(notification);
        return this;
    }

    /**
     * this function remove notification
     *
     * @param notification the notification
     * @return the User this
     */
    public User                     removeNotification(Notification notification) {
        this.notifications.remove(notification);
        return this;
    }

    /**
     * this function get areas
     *
     * @return a list of areas
     */
    public List<Area>               getAreas() {
        return areas;
    }

    /**
     * the setter for areas
     *
     * @param areas the areas
     * @return the User this
     */
    public User                     setAreas(List<Area> areas) {
        this.areas = areas;
        return this;
    }

    /**
     * this function add area
     *
     * @param area the area
     * @return the User this
     */
    public User                     addArea(Area area) {
        this.areas.add(area);
        return this;
    }

    /**
     * this function remove area
     *
     * @param area the area
     * @return the User this
     */
    public User                     removeArea(Area area) {
        this.areas.remove(area);
        return this;
    }

    /**
     * this function return a string with all information about
     * the user
     *
     * @return the string information
     */
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
