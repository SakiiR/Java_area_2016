package com.epitech.model;

import org.springframework.data.annotation.Id;
/**
 * Created by anakin on 14/12/16.
 */
public class                    User {

    @Id
    public String               id;

    public String               userName;
    public String               password;
    public String               salt;

    public                      User() { }

    public                      User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User                 setUsername(String userName) {
        this.userName = userName;
        return this;
    }

    public String               getUsername() {
        return this.userName;
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
                this.userName,
                this.password,
                this.salt
        );
    }


}
