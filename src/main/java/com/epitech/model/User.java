package com.epitech.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by anakin on 14/12/16.
 */

@Document(collection = "user")
public class                    User {

    @Id
    public String               id;

    public String               username;
    public String               password;
    public String               salt;

    public                      User() { }

    public                      User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User                 setUsername(String username) {
        this.username = username;
        return this;
    }

    public String               getUsername() {
        return this.username;
    }

    public User                 setPassword(String password) {
        this.password = password;
        return this;
    }

    public String               getPassword() {
        return this.password;
    }

    public User                 setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public String               getSalt() {
        return this.salt;
    }

    @Override
    public String               toString() {
        return String.format(
                "User[id=%s, userName='%s', password='%s', salt='%s']",
                this.id,
                this.username,
                this.password,
                this.salt
        );
    }


}
