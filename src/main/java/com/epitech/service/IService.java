package com.epitech.service;

/**
 * This interface is implemented by all
 * Services.
 */
public interface    IService {

    /**
     * Retrieve token by username and password.
     *
     * @param username the username to provide to the concerned API.
     * @param password the password to provide to the concerned API.
     * @return a token String
     */
    public String   login(String username, String password);
}
