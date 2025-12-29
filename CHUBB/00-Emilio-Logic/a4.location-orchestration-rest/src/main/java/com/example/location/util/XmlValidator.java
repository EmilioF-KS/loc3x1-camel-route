
package com.example.location.util;

import org.springframework.core.io.ClassPathResource;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.StringReader;

public class XmlValidator {
    private static final String XSD_PATH = "xsd/LocationRequest.xsd";

    public static void validateIfSchemaPresent(String xml) throws Exception {
        ClassPathResource res = new ClassPathResource(XSD_PATH);
        if (!res.exists()) return;
        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schema = factory.newSchema(res.getFile());
        Validator validator = schema.newValidator();
        try {
            validator.validate(new StreamSource(new StringReader(xml)));
        } catch (SAXException e) {
            throw new IllegalArgumentException("XML does not comply with LocationRequest.xsd: " + e.getMessage(), e);
        }
    }
}
