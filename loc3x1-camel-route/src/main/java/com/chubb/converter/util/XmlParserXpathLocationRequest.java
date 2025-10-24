package com.chubb.converter.util;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import com.locationretrievalloc3x1process.webservice.model.LocationRequest;

public class XmlParserXpathLocationRequest {
	public LocationRequest parseResponse(String xml) throws Exception {
    	//System.out.println("-> XmlParserXpathLocationRequest.parseResponse");
    	//System.out.println(xml);
    	
        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new ByteArrayInputStream(xml.getBytes()));

        XPath xpath = XPathFactory.newInstance().newXPath();
        
        String addressLine1 = xpath.evaluate("//AddressLine1", doc);
        String cityName = xpath.evaluate("//CityName", doc);
        String postalStateAbbreviation = xpath.evaluate("//PostalStateAbbreviation", doc);
        String postalCode = xpath.evaluate("//PostalCode", doc);
        String countryCode = xpath.evaluate("//CountryCode", doc);
        
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setAddressLine1(addressLine1);
        locationRequest.setCityName(cityName);
        locationRequest.setPostalStateAbbreviation(postalStateAbbreviation);
        locationRequest.setPostalCode(postalCode);
        locationRequest.setCountryCode(countryCode);
        
        //System.out.println("--> locationRequest : " + locationRequest);
        
        
        return locationRequest;
    }
}
