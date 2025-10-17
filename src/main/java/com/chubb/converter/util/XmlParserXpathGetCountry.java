package com.chubb.converter.util;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import com.chubb.xsd.ei.core.countryx1.Country;
import com.chubb.xsd.ei.corporate.getcountryreplycrp10x1.GetCountryReply;
import com.chubb.xsd.ei.corporate.getcountryrequestcrp10x2.GetCountryRequest;

public class XmlParserXpathGetCountry {
	public GetCountryReply parseResponse(String xml) throws Exception {
    	//System.out.println("-> XmlParserXpathGetCountry.parseResponse");
    	//System.out.println(xml);
    	
        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new ByteArrayInputStream(xml.getBytes()));

        XPath xpath = XPathFactory.newInstance().newXPath();
        
        String countryCode = xpath.evaluate("//Country/CountryCode", doc);
        String countryAbbreviation = xpath.evaluate("//Country/CountryAbbreviation", doc);
        String countryName = xpath.evaluate("//Country/CountryName", doc);
        String countryDescription = xpath.evaluate("//Country/CountryDescription", doc);
        String iSOCountryCode = xpath.evaluate("//Country/ISOCountryCode", doc);
        String iSOCountryDescription = xpath.evaluate("//Country/ISOCountryDescription", doc);
        String convertedISOCountryCode = xpath.evaluate("//Country/ConvertedISOCountryCode", doc);

        Country country = new Country();
        country.setCountryCode(countryCode);
        country.setCountryAbbreviation(countryAbbreviation);
        country.setCountryName(countryName);
        country.setISOCountryCode(iSOCountryCode);
        country.setConvertedISOCountryCode(convertedISOCountryCode);
        
        GetCountryReply getCountryReply = new GetCountryReply();
        getCountryReply.setCountry(country);
        System.out.println("--> getCountryReply : " + getCountryReply);
        
        
        return getCountryReply;
    }
}
