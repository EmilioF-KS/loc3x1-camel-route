package com.example.mock;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UkFloodController {
    @PostMapping(value = "/api/loc802x1/v1/get-uk-flood-information", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getUkFloodInformation() {
        String reply = "<GetUKFloodInformationReply xmlns=\"http://ei/location/get_uk_flood_information_reply_loc802x1\"/>";
        return ResponseEntity.ok(reply);
    }
}
