package com.epitech.model;

/**
 * Created by sakiir on 20/12/16.
 */

public class            UserModule {
    private Module      module;
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

    @Override
    public String toString() {
        return "UserModule{" +
                "module=" + module +
                ", token='" + token + '\'' +
                '}';
    }
}
