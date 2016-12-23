package com.epitech.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * This class is the representation
 * of a UserModule.
 */
public class            UserModule {

    @Id
    private String      id;

    @DBRef
    private Module      module;

    @DBRef
    private User        user;

    private String      token;

    public              UserModule() { }

    public              UserModule(Module module, String token) {
        this.module = module;
        this.token = token;
    }

    public Module       getModule() {
        return module;
    }

    public UserModule   setModule(Module module) {
        this.module = module;
        return this;
    }

    public String       getToken() {
        return token;
    }

    public UserModule   setToken(String token) {
        this.token = token;
        return this;
    }

    public User         getUser() {
        return user;
    }

    public UserModule   setUser(User user) {
        this.user = user;
        return this;
    }

    public String       getId() {
        return id;
    }

    public UserModule   setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "UserModule{" +
                "module=" + module +
                ", token='" + token + '\'' +
                '}';
    }
}
