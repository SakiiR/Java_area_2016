package com.epitech.service;

import com.epitech.model.Module;
import com.epitech.utils.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * the service for Slack
 */
public class                                        SlackService implements IService {

    /**
     * the constructor for the SlackService
     */
    public                                          SlackService() {}

    /**
     * the login method without oauth2 authentication
     * @param username the username to provide to the concerned API.
     * @param password the password to provide to the concerned API.
     * @return a string token
     */
    public String                                   login(String username, String password) {
        return "SLACK_TOKEN";
    }

    /**
     * the login method with an oauth2 authentication
     * @param code the code
     * @param module the module
     * @return a string token
     */
    public String                                   login(String code, Module module) {
        RestTemplate                                restTemplate = new RestTemplate();
        HttpHeaders                                 headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>>   request = new HttpEntity<>(null, headers);
        ResponseEntity<String>                      response = restTemplate.postForEntity(module.getTokenUrl() + String.format("&code=%s", code), request, String.class);

        Logger.logWarning("Received from %s : %s", module.getName(), response.getBody());
        try {
            HashMap<String, Object> json = new ObjectMapper().readValue(response.getBody(), HashMap.class);
            return (String) json.get("access_token");
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logWarning("Failed to retrieve access token ..");
        }
        return null;
    }
}
