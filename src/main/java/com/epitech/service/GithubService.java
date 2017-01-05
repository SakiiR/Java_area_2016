package com.epitech.service;

import com.epitech.model.Module;
import com.epitech.utils.BodyParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class                                        GithubService implements IService {
    public                                          GithubService() {}

    public String                                   login(String username, String password) {
        return "GITHUB_TOKEN";
    }

    public String                                   login(String code, Module module) {
        RestTemplate                                restTemplate = new RestTemplate();
        HttpHeaders                                 headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>>   request = new HttpEntity<>(null, headers);
        ResponseEntity<String>                      response = restTemplate.postForEntity(module.getTokenUrl() + String.format("&code=%s", code), request, String.class);
        BodyParser                                  bodyParser = new BodyParser(response.getBody());

        return bodyParser.get("access_token");
    }
}
