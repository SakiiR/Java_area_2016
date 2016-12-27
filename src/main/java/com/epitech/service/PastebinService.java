package com.epitech.service;

import com.epitech.utils.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class                                PastebinService implements IService {
    private RestTemplate                    rest;

    public                                  PastebinService() {
        this.rest = new RestTemplate();
    }

    public static String                    api_dev_key = "221ed12ce0e20b3d32fed3b3545346f7";

    public String                           login(String username, String password) {
        MultiValueMap<String, String>       postParameters = new LinkedMultiValueMap<>();
        String                              result = null;
        HttpHeaders                         headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        postParameters.add("api_dev_key", PastebinService.api_dev_key);
        postParameters.add("api_user_name", username);
        postParameters.add("api_user_password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(postParameters, headers);
        ResponseEntity<String> response = this.rest.postForEntity("http://pastebin.com/api/api_login.php", request, String.class);
        if (!(response.getBody().contains("Bad API request"))) {
            result = response.getBody();
        }
        return result;
    }
}
