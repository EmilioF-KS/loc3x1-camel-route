package com.example.mock.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockApiController {
  @PostMapping(path = "/api/LocationRetrievalLOC3X2/GetLocation", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> invoke(@RequestBody String xml) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body("<ok/>");
  }
  @PostMapping(path = "/api/LocationRetrievalLOC3X2B/GetLocation3X2B", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> invoke(@RequestBody String xml) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body("<ok/>");
  }
}
