package com.example.mock;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressStandardizationController {
    @PostMapping(value = "/api/loc2x2/v1/standardize-us-address", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> standardizeUS() {
        String reply = "<StandardizeUSAddressReply xmlns=\"http://ei/location/standardize_us_address_reply_loc2x2\">" +
                "<StandardizedAddress xmlns=\"http://ei/location/standardized_addressx2\"/>" +
                "</StandardizeUSAddressReply>";
        return ResponseEntity.ok(reply);
    }

    @PostMapping(value = "/api/loc2x2/v1/standardize-osus-address", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> standardizeOSUS() {
        String reply = "<StandardizeOSUSAddressReply xmlns=\"http://ei/location/standardize_osus_address_reply_loc2x2\">" +
                "<StandardizedAddress xmlns=\"http://ei/location/standardized_addressx2\"/>" +
                "</StandardizeOSUSAddressReply>";
        return ResponseEntity.ok(reply);
    }
}
