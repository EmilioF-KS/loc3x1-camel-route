package com.example.mock.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockApiController {
  @PostMapping(path = "/api/LocationRetrievalLOC3X3/GetLocation", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> invokeLocationRetrievalLOC3X3GetLocation(@RequestBody String xml) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body("<ok/>");
  }
  @PostMapping(path = "/api/LocationRetrievalLOC3X3/GetLocationList", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> invokeLocationRetrievalLOC3X3GetLocationList(@RequestBody String xml) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body("<ok/>");
  }
  @PostMapping(path = "/api/LocationRetrievalLOC3X3B/GetLocation", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> invokeLocationRetrievalLOC3X3BGetLocation(@RequestBody String xml) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body("<ok/>");
  }
  @PostMapping(path = "/api/LocationRetrievalLOC3X3B/GetLocationList", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> invokeLocationRetrievalLOC3X3BGetLocationList(@RequestBody String xml) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body("<ok/>");
  }
  @PostMapping(path = "/api/LocationRetrievalLOC3X3M/GetLocation3X3M", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> invokeLocationRetrievalLOC3X3MGetLocation3X3M(@RequestBody String xml) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body("<ok/>");
  }
  @PostMapping(path = "/api/LocationRetrievalLOC3X3M/GetLocationList3X3M", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> invokeLocationRetrievalLOC3X3MGetLocationList3X3M(@RequestBody String xml) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body("<ok/>");
  }
}
