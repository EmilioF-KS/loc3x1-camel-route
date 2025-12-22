package com.example.mock;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Crp11x1Controller {
    @PostMapping(value = "/api/crp11x1/v1/get-state-or-province", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getStateOrProvince() {
        String reply = "<GetStateOrProvinceReply xmlns=\"http://ei/corporate/get_state_or_province_reply_crp11x1\"/>";
        return ResponseEntity.ok(reply);
    }
}

