package com.example.mock;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Crp10x1Controller {
    @PostMapping(value = "/api/crp10x1/v1/get-country", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getCountry() {
        String reply = "<GetCountryReply xmlns=\"http://ei/corporate/get_country_reply_crp10x1\"/>";
        return ResponseEntity.ok(reply);
    }
}

