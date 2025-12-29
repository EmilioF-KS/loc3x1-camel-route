package com.summary.mock.api.selected;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LocationRetrievalLOC3X1GetLocationWithTaxingJurisdictionsController {
  private static final Logger log = LoggerFactory.getLogger(LocationRetrievalLOC3X1GetLocationWithTaxingJurisdictionsController.class);
  private static final String SERVICE_DIR = "selected";
  @PostMapping(path = "/api/mock/LocationRetrievalLOC3X1/GetLocationWithTaxingJurisdictions", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> invoke(@RequestBody String xml, @RequestParam(name="validate", required=false) Boolean validate) {
    try {
      if (validate != null && validate) {
        // request XSD not found; skipping schema validation
      }
      String root = null;
      String body = (root != null) ? ("<" + root + "><status>OK</status></" + root + ">") : ("<" + "GetLocationWithTaxingJurisdictions" + "Response><status>OK</status></" + "GetLocationWithTaxingJurisdictions" + "Response>");
      log.info("mock {} request ok", "LocationRetrievalLOC3X1/GetLocationWithTaxingJurisdictions");
      return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(body);
    } catch (Exception e) {
      log.error("mock {} error: {}", "LocationRetrievalLOC3X1/GetLocationWithTaxingJurisdictions", e.getMessage(), e);
      return ResponseEntity.status(400).contentType(MediaType.APPLICATION_XML).body("<error>invalid request</error>");
    }
  }
}
