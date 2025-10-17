package com.chubb.converter.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public class XmlParser {

    public GetStateOrProvinceResponse parseResponse(String xml) throws Exception {
    	//System.out.println("-> XmlParser.GetStateOrProvinceResponse");
    	//System.out.println(xml);
    	
        JAXBContext context = JAXBContext.newInstance(GetStateOrProvinceResponse.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (GetStateOrProvinceResponse) unmarshaller.unmarshal(new StringReader(xml));
    }
}
