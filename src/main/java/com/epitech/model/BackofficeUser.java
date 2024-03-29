package com.epitech.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * This class represent a user
 * in the database.
 */
@Document(collection = "backoffice_user")
public class    BackofficeUser {
    @Id
    private String                  id;

    private String                  username;
    private String                  password;
    private String                  salt;

    /**
     * The constructor for the BackofficeUser
     */
    public                          BackofficeUser() { }

    /**
     * The constructor for the BackofficeUser
     * @param username the user name
     * @param password the user password
     */
    public                          BackofficeUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * the setter for the username
     *
     * @param username the username to set.
     * @return a BackofficeUser this
     */
    public BackofficeUser           setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * the getter for the username
     *
     * @return a sting username
     */
    public String                   getUsername() {
        return this.username;
    }

    /**
     * the setter for the password
     *
     * @param password the password to set.
     * @return a BackofficeUser this
     */
    public BackofficeUser           setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * the getter for the user password
     *
     * @return a string password
     */
    public String                   getPassword() {
        return this.password;
    }

    /**
     * the setter for the salt
     *
     * @param salt the salt to set.
     * @return a BackofficeUser this
     */
    public BackofficeUser           setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    /**
     * the getter for the salt
     *
     * @return a string salt
     */
    public String                   getSalt() {
        return this.salt;
    }

    /**
     * this function return all the informations for a user
     *
     * @return a string informations
     */
    @Override
    public String                   toString() {
        return String.format(
                "BackofficeUser[id=%s, userName='%s', password='%s', salt='%s']",
                this.id,
                this.username,
                this.password,
                this.salt
        );
    }
}
