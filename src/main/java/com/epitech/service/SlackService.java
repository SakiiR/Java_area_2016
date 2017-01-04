package com.epitech.service;

import com.epitech.model.Module;
import com.epitech.model.UserModule;
import com.epitech.utils.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

public class                                        SlackService implements IService {
    public                                          SlackService() {}

    public String                                   login(String username, String password) {
        return "SLACK_TOKEN";
    }

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
