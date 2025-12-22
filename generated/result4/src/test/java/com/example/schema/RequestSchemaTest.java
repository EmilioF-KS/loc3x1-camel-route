package com.example.schema;

import com.example.validation.XmlSchemaValidator;
import org.junit.jupiter.api.Test;

public class RequestSchemaTest {
    XmlSchemaValidator validator = new XmlSchemaValidator();

    @Test
    void validateGetCountryRequest() throws Exception {
        String xml = "<GetCountryRequest xmlns=\"http://ei/corporate/get_country_request_crp10x1\"><CountryCode>US</CountryCode></GetCountryRequest>";
        validator.validateBody(xml, "contracts/selected/CountryRetrievalCRP10X1/GetCountryRequest.xsd");
    }

    @Test
    void validateGetStateOrProvinceRequest() throws Exception {
        String xml = "<GetStateOrProvinceRequest xmlns=\"http://ei/corporate/get_state_or_province_request_crp11x1\"><StateOrProvinceCode>CA</StateOrProvinceCode></GetStateOrProvinceRequest>";
        validator.validateBody(xml, "contracts/selected/StateOrProvinceRetrievalCRP11X1/GetStateOrProvinceRequest.xsd");
    }

    @Test
    void validateGetLocationListRequest() throws Exception {
        String xml = "<GetLocationListRequest xmlns=\"http://ei/location/get_location_list_request_loc3x1b\"><AddressLine1>1 Main</AddressLine1><CityName>San Francisco</CityName><StateOrProvinceCode>CA</StateOrProvinceCode><CountryCode>US</CountryCode></GetLocationListRequest>";
        validator.validateBody(xml, "contracts/selected/LocationRetrievalLOC3X1B/GetLocationListRequest.xsd");
    }
}
