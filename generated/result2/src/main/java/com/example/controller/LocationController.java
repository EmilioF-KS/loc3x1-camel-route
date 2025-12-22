package com.example.controller;

import org.apache.camel.ProducerTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/loc")
public class LocationController {

    private final ProducerTemplate producerTemplate;

    public LocationController(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

    @PostMapping(value = "/GetLocationList", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getLocationList(@RequestBody String xml) {
        String reply = producerTemplate.requestBody("direct:getLocationList", xml, String.class);
        return ResponseEntity.ok(reply);
    }

    @PostMapping(value = "/GetLocation", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getLocation(@RequestBody String xml) {
        String reply = producerTemplate.requestBody("direct:getLocation", xml, String.class);
        return ResponseEntity.ok(reply);
    }
}

