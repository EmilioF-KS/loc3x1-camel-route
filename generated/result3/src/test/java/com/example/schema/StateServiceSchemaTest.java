package com.example.schema;

import com.example.validation.XmlSchemaValidator;
import org.junit.jupiter.api.Test;

public class StateServiceSchemaTest {
    XmlSchemaValidator validator = new XmlSchemaValidator();

    @Test
    void validateGetStateRequest() throws Exception {
        String xml = "<GetStateOrProvinceRequest xmlns=\"http://ei/corporate/get_state_or_province_request_crp11x1\"><StateOrProvinceCode>CA</StateOrProvinceCode></GetStateOrProvinceRequest>";
        validator.validateBody(xml, "contracts/selected/StateOrProvinceRetrievalCRP11X1/GetStateOrProvinceRequest.xsd");
    }

    @Test
    void validateGetStateReply() throws Exception {
        String xml = "<GetStateOrProvinceListReply xmlns=\"http://ei/corporate/get_state_or_province_list_reply_crp11x1\"><StateOrProvince><StateOrProvinceCode>CA</StateOrProvinceCode></StateOrProvince></GetStateOrProvinceListReply>";
        validator.validateBody(xml, "contracts/selected/StateOrProvinceRetrievalCRP11X1/GetStateOrProvinceListReply.xsd");
    }
}
