package com.example.schema;

import com.example.validation.XmlSchemaValidator;
import org.junit.jupiter.api.Test;

public class Loc3x1bServiceSchemaTest {
    XmlSchemaValidator validator = new XmlSchemaValidator();

    @Test
    void validateGetLocationListRequest() throws Exception {
        String xml = "<GetLocationListRequest xmlns=\"http://ei/location/get_location_list_request_loc3x1b\"><AddressLine1>1 Main</AddressLine1><CityName>San Francisco</CityName><StateOrProvinceCode>CA</StateOrProvinceCode><CountryCode>US</CountryCode></GetLocationListRequest>";
        validator.validateBody(xml, "contracts/selected/LocationRetrievalLOC3X1B/GetLocationListRequest.xsd");
    }

    @Test
    void validateLocationListReply() throws Exception {
        String xml = "<LocationListReply xmlns=\"http://ei/location/location_list_reply_loc3x1b\"><Location/><StatusInformation/></LocationListReply>";
        validator.validateBody(xml, "contracts/selected/LocationRetrievalLOC3X1B/LocationListReply.xsd");
    }
}
