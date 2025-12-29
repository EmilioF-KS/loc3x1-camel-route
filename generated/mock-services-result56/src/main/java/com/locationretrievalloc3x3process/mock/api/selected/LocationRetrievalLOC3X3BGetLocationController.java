package com.locationretrievalloc3x3process.mock.api.selected;

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
public class LocationRetrievalLOC3X3BGetLocationController {
  private static final Logger log = LoggerFactory.getLogger(LocationRetrievalLOC3X3BGetLocationController.class);
  private static final String SERVICE_DIR = "selected";
  @PostMapping(path = "/api/mock/LocationRetrievalLOC3X3B/GetLocation", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<String> invoke(@RequestBody String xml, @RequestParam(name="validate", required=false) Boolean validate) {
    try {
      if (validate != null && validate) {
        try (InputStream is = new ClassPathResource("contracts\selected\GetLocationListRequest.xsd").getInputStream()) {
          SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
          Schema schema = sf.newSchema(new StreamSource(is));
          Validator v = schema.newValidator();
          v.validate(new StreamSource(new StringReader(xml)));
        } catch (Exception e) {
          log.error("validation failed: {}", e.getMessage(), e);
          return ResponseEntity.status(400).contentType(MediaType.APPLICATION_XML).body("<error>invalid request</error>");
        }
      }
      String root = null;
      try (InputStream is2 = new ClassPathResource("contracts\selected\GetLocationListReply.xsd").getInputStream()) {
        String xsd = new String(is2.readAllBytes(), StandardCharsets.UTF_8);
        Matcher m = Pattern.compile("<\\s*xs:element\\s+name=\\\"([^\\\"]+)\\\"").matcher(xsd);
        if (m.find()) { root = m.group(1); }
      } catch (Exception ignored) {}
      String body = (root != null) ? ("<" + root + "><status>OK</status></" + root + ">") : ("<" + "GetLocation" + "Response><status>OK</status></" + "GetLocation" + "Response>");
      log.info("mock {} request ok", "LocationRetrievalLOC3X3B/GetLocation");
      return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(body);
    } catch (Exception e) {
      log.error("mock {} error: {}", "LocationRetrievalLOC3X3B/GetLocation", e.getMessage(), e);
      return ResponseEntity.status(400).contentType(MediaType.APPLICATION_XML).body("<error>invalid request</error>");
    }
  }
}
