package com.chubb.converter.util;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

public class XmlParserXpathGetStateOrProvince {

    public String parseResponse(String xml) throws Exception {
    	//System.out.println("-> XmlParserXpathGetStateOrProvince.parseResponse");
    	//System.out.println(xml);
    	
        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new ByteArrayInputStream(xml.getBytes()));

        XPath xpath = XPathFactory.newInstance().newXPath();

        String code = xpath.evaluate("//StateOrProvince/StateOrProvinceCode", doc);
        String name = xpath.evaluate("//StateOrProvince/StateOrProvinceName", doc);
        String abbreviation = xpath.evaluate("//StateOrProvince/PostalStateAbbreviation", doc);

        return code;
    }
}
