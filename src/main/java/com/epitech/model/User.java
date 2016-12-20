package com.epitech.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by anakin on 14/12/16.
 */

@Document(collection = "user")
public class                        User {

    @Id
    private String                  id;

    private String                  username;
    private String                  password;
    private String                  salt;
    private List<UserModule>        modules;

    public                          User() { }

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
