package com.epitech.service;

import com.epitech.model.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * the service for Yammer
 */
public class                                        YammerService implements IService {

    /**
     * the constructor for the YammerService
     */
    public                                          YammerService() {}

    /**
     * the login method for authentication without oauth2
     * @param username the username to provide to the concerned API.
     * @param password the password to provide to the concerned API.
     * @return a token string
     */
    public String                                   login(String username, String password) {
        return "YAMMER_TOKEN";
    }

    /**
     * the login method for authentication with oauth2
     * @param code the code
     * @param module the module
     * @return the string token
     */
    public String                                   login(String code, Module module) {
        RestTemplate                                restTemplate = new RestTemplate();
        HttpHeaders                                 headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>>   request = new HttpEntity<>(null, headers);
        ResponseEntity<String>                      response = restTemplate.postForEntity(module.getTokenUrl() + String.format("&code=%s", code), request, String.class);

        try {
            HashMap<String, Object> json = new ObjectMapper().readValue(response.getBody(), HashMap.class);
            HashMap<String, Object> token = (HashMap<String, Object>) json.get("access_token");

            return (String) token.get("token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
