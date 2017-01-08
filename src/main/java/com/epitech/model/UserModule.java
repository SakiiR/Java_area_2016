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

    /**
     * the constructor for userModule
     */
    public              UserModule() { }

    /**
     * the constructor for UserModule
     *
     * @param module the module
     * @param token the token from oauth2 connexion
     */
    public              UserModule(Module module, String token) {
        this.module = module;
        this.token = token;
    }

    /**
     * the getter for module
     *
     * @return the module to get
     */
    public Module       getModule() {
        return module;
    }

    /**
     * the setter for module
     *
     * @param module the module
     * @return this UserModule this
     */
    public UserModule   setModule(Module module) {
        this.module = module;
        return this;
    }

    /**
     * the getter for token
     *
     * @return the string token
     */
    public String       getToken() {
        return token;
    }

    /**
     * the setter for token
     *
     * @param token the token
     * @return the UserModule this
     */
    public UserModule   setToken(String token) {
        this.token = token;
        return this;
    }

    /**
     * the getter for user
     *
     * @return the user
     */
    public User         getUser() {
        return user;
    }

    /**
     * the setter for user
     *
     * @param user the user
     * @return the UserModule this
     */
    public UserModule   setUser(User user) {
        this.user = user;
        return this;
    }

    /**
     * the getter for id
     *
     * @return the string id
     */
    public String       getId() {
        return id;
    }

    /**
     * the setter for id
     *
     * @param id the id
     * @return the UserModule this
     */
    public UserModule   setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * this function return a string information for the UserModule
     *
     * @return a string information
     */
    @Override
    public String toString() {
        return "UserModule{" +
                "module=" + module +
                ", token='" + token + '\'' +
                '}';
    }
}
