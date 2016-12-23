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

    public                          BackofficeUser() { }

    public                          BackofficeUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public BackofficeUser           setUsername(String username) {
        this.username = username;
        return this;
    }

    public String                   getUsername() {
        return this.username;
    }

    public BackofficeUser           setPassword(String password) {
        this.password = password;
        return this;
    }

    public String                   getPassword() {
        return this.password;
    }

    public BackofficeUser           setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public String                   getSalt() {
        return this.salt;
    }

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
