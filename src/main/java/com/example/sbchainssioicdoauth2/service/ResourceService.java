package com.example.sbchainssioicdoauth2.service;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ResourceService {

    public String getSomething(KeycloakSecurityContext context) {
        final String uri = "http://localhost:8081/resources/something";

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(context.getTokenString());

        HttpEntity<?> httpEntity = new HttpEntity<Object>(requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class).getBody();
        log.info("cccccccccccccccccccc result :{}", result);
        return result;
    }

    
}