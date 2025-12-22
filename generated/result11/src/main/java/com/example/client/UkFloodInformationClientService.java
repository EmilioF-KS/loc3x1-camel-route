package com.example.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UkFloodInformationClientService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${providers.mock.baseUrl}")
    private String baseUrl;

    public String getInformation(String requestXml) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<String> entity = new HttpEntity<>(requestXml, headers);
        String url = baseUrl + "/api/loc802x1/v1/get-uk-flood-information";
        return restTemplate.postForObject(url, entity, String.class);
    }
}
