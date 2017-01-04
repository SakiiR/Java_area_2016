package com.epitech.service;

import com.epitech.model.Module;
import com.epitech.utils.BodyParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

public class            DropboxService implements IService {
    public              DropboxService() {  }

    public String       login(String username, String password) {
        return "DROPBOX_TOKEN";
    }

    public String       login(String code, Module module) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(module.getTokenUrl() + String.format("&code=%s", code), request, String.class);
        BodyParser bodyParser = new BodyParser(response.getBody());

        try {
            HashMap<String, Object> json = new ObjectMapper().readValue(response.getBody(), HashMap.class);
            return (String) json.get("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

