package com.epitech.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.gmail.Gmail;

public class DriveService implements IService {
    public DriveService() {}

    public String       login(String username, String password) {
        return "GOOGLEDRIVE_TOKEN";
    }

    public Drive        getDriveService(String token) {
        Drive           service;
        GoogleCredential credential = new GoogleCredential().setAccessToken(token);
        JsonFactory jsonFactory = new JacksonFactory();
        HttpTransport httpTransport = new NetHttpTransport();

        service = new Drive.Builder(httpTransport, jsonFactory, credential).setApplicationName("DriveApi").build();
        return service;
    }
}
