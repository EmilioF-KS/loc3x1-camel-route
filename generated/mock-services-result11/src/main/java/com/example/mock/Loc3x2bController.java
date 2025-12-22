package com.example.mock;

import java.nio.charset.StandardCharsets;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Loc3x2bController {
    @PostMapping(value = "/api/loc3x2b/v1/get-location", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getLocation() throws Exception {
        ClassPathResource res = new ClassPathResource("mocks/loc3x2b/GetLocationReply.xml");
        String xml = StreamUtils.copyToString(res.getInputStream(), StandardCharsets.UTF_8);
        String envelope = "<GetLocation3X2BResponse xmlns=\"http://ei/location/location_retrieval_loc3x2b\"><GetLocationReply xmlns=\"http://ei/location/get_location_reply_loc3x2b\">" +
                xml + "</GetLocationReply></GetLocation3X2BResponse>";
        return ResponseEntity.ok(envelope);
    }
}
