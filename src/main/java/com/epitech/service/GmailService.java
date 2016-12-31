package com.epitech.service;

import com.google.api.services.gmail.Gmail;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;


public class                GmailService implements IService {
    public                  GmailService() {}

    public String           login(String username, String password) {
        return "GMAIL_TOKEN";
    }

    public Gmail            buildGmailService(String token) {
        Gmail               service;
        GoogleCredential    credential = new GoogleCredential().setAccessToken(token);
        JsonFactory         jsonFactory = new JacksonFactory();
        HttpTransport       httpTransport = new NetHttpTransport();

        service = new Gmail.Builder(httpTransport, jsonFactory, credential).setApplicationName("GmailApi").build();
        return service;
    }
}