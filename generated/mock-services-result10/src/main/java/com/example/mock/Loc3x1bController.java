package com.example.mock;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Loc3x1bController {
    @PostMapping(value = "/api/loc3x1b/v1/get-location-list", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getLocationList() {
        String reply = "<GetLocationListReply xmlns=\"http://ei/location/get_location_list_reply_loc3x1b\"/>";
        return ResponseEntity.ok(reply);
    }
}

