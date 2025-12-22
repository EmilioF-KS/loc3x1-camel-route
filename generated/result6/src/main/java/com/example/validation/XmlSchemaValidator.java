package com.example.validation;

import org.springframework.stereotype.Component;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.StringReader;

@Component
public class XmlSchemaValidator {
    public void validateBody(String xml, String xsdClasspath) throws Exception {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        var url = getClass().getClassLoader().getResource(xsdClasspath);
        if (url == null) {
            throw new IllegalArgumentException("XSD not found: " + xsdClasspath);
        }
        Schema schema = sf.newSchema(url);
        Validator validator = schema.newValidator();
        StreamSource source = new StreamSource(new StringReader(xml));
        source.setSystemId("in-memory");
        validator.validate(source);
    }
}
