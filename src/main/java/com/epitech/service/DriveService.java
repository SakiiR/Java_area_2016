package com.epitech.service;

import com.epitech.model.Module;
import com.epitech.utils.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * The service for Google Drive
 */
public class DriveService implements IService {

    /**
     * the constructor for DriveService
     */
    public DriveService() {}

    /**
     * the function login without oauth
     * @param username the username to provide to the concerned API.
     * @param password the password to provide to the concerned API.
     * @return a sting token
     */
    public String       login(String username, String password) {
        return "GOOGLEDRIVE_TOKEN";
    }

    /**
     * the getter for Drive service
     * @param token the token from oauth2
     * @return the Drive service
     */
    public Drive        getDriveService(String token) {
        Drive           service;
        GoogleCredential credential = new GoogleCredential().setAccessToken(token);
        JsonFactory jsonFactory = new JacksonFactory();
        HttpTransport httpTransport = new NetHttpTransport();

        service = new Drive.Builder(httpTransport, jsonFactory, credential).setApplicationName("DriveApi").build();
        return service;
    }

    /**
     * this function log the user to the GoogleDrive Service with an Oauth2
     * authentication
     * @param code the string code
     * @param module the module
     * @return a string token
     */
    public String       login(String code, Module module) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(module.getTokenUrl() + String.format("&code=%s", code), request, String.class);

        try {
            HashMap<String, Object> json = new ObjectMapper().readValue(response.getBody(), HashMap.class);
            return (String) json.get("access_token");
        } catch (Exception e) {
            e.printStackTrace();
            Logger.logInfo("Failed to retreive google token : %s", response.getBody());
        }

        return null;
    }
}
