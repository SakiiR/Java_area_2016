package com.epitech.service;

import com.epitech.model.Module;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * the service for Pastebin
 */
public class                                PastebinService implements IService {
    private RestTemplate                    rest;

    /**
     * the constructor for PastebinService
     */
    public                                  PastebinService() {
        this.rest = new RestTemplate();
    }

    public static String                    api_dev_key = "221ed12ce0e20b3d32fed3b3545346f7";

    /**
     * the login method without oauth authentication
     * @param username the username to provide to the concerned API.
     * @param password the password to provide to the concerned API.
     * @return a string token
     */
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

    /**
     * a login method without an oauth2 authentication
     * @param code the code
     * @param module the module
     * @return the string token
     */
    public String       login(String code, Module module) {
        return "PASTEBIN_TOKEN";
    }
}
