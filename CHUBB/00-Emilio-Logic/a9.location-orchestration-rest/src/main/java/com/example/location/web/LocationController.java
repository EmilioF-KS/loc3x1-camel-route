
package com.example.location.web;

import com.example.location.model.LocationRequest;
import com.example.location.util.XmlValidator;
import com.example.location.xsd.LocationRequestXsd;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/controller/locations")
@RequiredArgsConstructor
public class LocationController {

    private final ProducerTemplate producerTemplate;

    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLocationListJson(@RequestBody LocationRequest body) {
        Object reply = producerTemplate.requestBody("direct:getLocationList", body);
        return ResponseEntity.ok(reply);
    }

    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLocationListXml(@RequestBody String xml) {
        try {
            XmlValidator.validateIfSchemaPresent(xml);
            XmlMapper xmlMapper = new XmlMapper();
            LocationRequestXsd x = xmlMapper.readValue(xml, LocationRequestXsd.class);
            LocationRequest req = toInternal(x);
            Object reply = producerTemplate.requestBody("direct:getLocationList", req);
            return ResponseEntity.ok(reply);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Invalid XML: " + ex.getMessage());
        }
    }

    private LocationRequest toInternal(LocationRequestXsd x) {
        LocationRequest r = new LocationRequest();
        r.setAddressLine1(x.getAddressLine1());
        r.setAddressLine2(x.getAddressLine2());
        r.setCityName(x.getCityName());
        r.setStateOrProvinceCode(x.getStateOrProvinceCode());
        r.setPostalStateAbbreviation(x.getPostalStateAbbreviation());
        r.setPostalCode(x.getPostalCode());
        r.setLocationPlaceCode(x.getLocationPlaceCode());
        r.setCountryCode(x.getCountryCode());
        r.setCountryAbbreviation(x.getCountryAbbreviation());
        return r;
    }
}
