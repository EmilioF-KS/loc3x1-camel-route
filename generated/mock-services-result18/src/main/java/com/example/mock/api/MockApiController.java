package com.example.mock.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockApiController {
  @PostMapping(path = "/api/CountryRetrievalCRP10X1/GetCountry", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> invoke(@RequestBody String xml) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body("<ok/>");
  }
  @PostMapping(path = "/api/CountryRetrievalCRP10X1/GetCountryList", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> invoke(@RequestBody String xml) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body("<ok/>");
  }
  @PostMapping(path = "/api/LocationRetrievalLOC3X1/GetLocationList", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> invoke(@RequestBody String xml) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body("<ok/>");
  }
  @PostMapping(path = "/api/LocationRetrievalLOC3X1/GetLocationWithTaxingJurisdictions", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> invoke(@RequestBody String xml) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body("<ok/>");
  }
  @PostMapping(path = "/api/LocationRetrievalLOC3X1B/GetLocationList3X1B", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> invoke(@RequestBody String xml) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body("<ok/>");
  }
  @PostMapping(path = "/api/LocationRetrievalLOC3X1B/GetLocationWithTaxingJurisdictions3X1B", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> invoke(@RequestBody String xml) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body("<ok/>");
  }
  @PostMapping(path = "/api/LocationRetrievalLOC3X1M/GetLocationList3X1M", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> invoke(@RequestBody String xml) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body("<ok/>");
  }
  @PostMapping(path = "/api/LocationRetrievalLOC3X1M/GetLocationWithTaxingJurisdictions3X1M", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> invoke(@RequestBody String xml) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body("<ok/>");
  }
  @PostMapping(path = "/api/StateOrProvinceRetrievalCRP11X1/GetStateOrProvince", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> invoke(@RequestBody String xml) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body("<ok/>");
  }
  @PostMapping(path = "/api/StateOrProvinceRetrievalCRP11X1/GetStateOrProvinceList", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> invoke(@RequestBody String xml) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body("<ok/>");
  }
}
