package com.example.mock.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationRetrievalLOC3X1MGetLocationList3X1MController {
  @PostMapping(path = "/api/LocationRetrievalLOC3X1M/GetLocationList3X1M", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> invoke(@RequestBody String xml) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body("<ok/>");
  }
}
