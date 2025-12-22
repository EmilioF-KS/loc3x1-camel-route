package com.example.schema;

import com.example.validation.XmlSchemaValidator;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class MockSchemaTest {
    XmlSchemaValidator validator = new XmlSchemaValidator();

    private String readResource(String path) throws Exception {
        var url = getClass().getClassLoader().getResource(path);
        if (url == null) throw new IllegalArgumentException("Missing resource: " + path);
        return Files.readString(Path.of(url.toURI()), StandardCharsets.UTF_8);
    }

    @Test
    void crp10x1ReplySchemaValidates() throws Exception {
        String xml = readResource("mocks/crp10x1/GetCountryReply.xml");
        validator.validateBody(xml, "contracts/selected/CountryRetrievalCRP10X1/GetCountryReply.xsd");
    }

    @Test
    void crp11x1ReplySchemaValidates() throws Exception {
        String xml = readResource("mocks/crp11x1/GetStateOrProvinceListReply.xml");
        validator.validateBody(xml, "contracts/selected/StateOrProvinceRetrievalCRP11X1/GetStateOrProvinceListReply.xsd");
    }

    @Test
    void loc3x1bReplySchemaValidates() throws Exception {
        String xml = readResource("mocks/loc3x1b/LocationListReply.xml");
        validator.validateBody(xml, "contracts/selected/LocationRetrievalLOC3X1B/LocationListReply.xsd");
    }
}

