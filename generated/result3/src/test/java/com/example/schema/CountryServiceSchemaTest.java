package com.example.schema;

import com.example.validation.XmlSchemaValidator;
import org.junit.jupiter.api.Test;

public class CountryServiceSchemaTest {
    XmlSchemaValidator validator = new XmlSchemaValidator();

    @Test
    void validateGetCountryRequest() throws Exception {
        String xml = "<GetCountryRequest xmlns=\"http://ei/corporate/get_country_request_crp10x1\"><CountryCode>US</CountryCode></GetCountryRequest>";
        validator.validateBody(xml, "contracts/selected/CountryRetrievalCRP10X1/GetCountryRequest.xsd");
    }

    @Test
    void validateGetCountryReply() throws Exception {
        String xml = "<GetCountryReply xmlns=\"http://ei/corporate/get_country_reply_crp10x1\"><Country><CountryCode>US</CountryCode></Country></GetCountryReply>";
        validator.validateBody(xml, "contracts/selected/CountryRetrievalCRP10X1/GetCountryReply.xsd");
    }
}
