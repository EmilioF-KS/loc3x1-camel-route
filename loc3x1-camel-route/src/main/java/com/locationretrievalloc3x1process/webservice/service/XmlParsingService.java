package com.locationretrievalloc3x1process.webservice.service;

import java.io.StringReader;

import org.springframework.stereotype.Service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

@Service
public class XmlParsingService {

    public <T> T convertXmlToObject(String xml, Class<T> clazz) throws Exception {
    	System.out.println("xml : " + xml);
    	System.out.println("clazz : " + clazz);
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        StringReader reader = new StringReader(xml);
        return (T) unmarshaller.unmarshal(reader);
    }
}
