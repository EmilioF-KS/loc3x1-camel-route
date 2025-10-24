package com.chubb.converter.util;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.chubb.xsd.dto.rand.chubb.com.StatusInformation;
import com.chubb.xsd.ei.core.taxingjurisdictionx1.*;
import com.chubb.xsd.ei.location.locationx5.*;
import com.chubb.xsd.ei.location.locationinformationx2.*;
import com.chubb.xsd.ei.location.locationlistreplyloc3x1b.LocationListReply;
import com.chubb.xsd.ei.location.standardizedaddressx2.StandardizedAddress;

public class XmlParserXpathLocation {

    public LocationListReply parseResponse(String xml) throws Exception {
    	System.out.println("-> XmlParserXpathLocation.parseResponse");
    	System.out.println(xml);
    	
        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new ByteArrayInputStream(xml.getBytes()));

        XPath xpath = XPathFactory.newInstance().newXPath();

        String cityCode = xpath.evaluate("//Location/LocationInformation/CityCode", doc);
        String countyCode = xpath.evaluate("//Location/LocationInformation/CountyCode", doc);
        String licenseCode = xpath.evaluate("//Location/LocationInformation/LicenseCode", doc);
        String fireDistrictCode = xpath.evaluate("//Location/LocationInformation/FireDistrictCode", doc);
        
        LocationInformation locationInformation = new LocationInformation();
        locationInformation.setCityCode(cityCode);
        locationInformation.setCountyCode(countyCode);
        locationInformation.setLicenseCode(licenseCode);
        locationInformation.setFireDistrictCode(fireDistrictCode);
        
        //////////////////////////////////////////////////////
        /////TODO StandardizedAddress
        String cityName = xpath.evaluate("//Location/StandardizedAddress/CityName", doc);
        String countyName = xpath.evaluate("//Location/StandardizedAddress/CountyName", doc);
        String stateOrProvinceCode = xpath.evaluate("//Location/StandardizedAddress/StateOrProvinceCode", doc);
        String stateOrProvinceName = xpath.evaluate("//Location/StandardizedAddress/StateOrProvinceName", doc);
        String postalStateAbbreviation = xpath.evaluate("//Location/StandardizedAddress/PostalStateAbbreviation", doc);
        String postalCode = xpath.evaluate("//Location/StandardizedAddress/PostalCode", doc);
        String countryCode = xpath.evaluate("//Location/StandardizedAddress/CountryCode", doc);
        String countryName = xpath.evaluate("//Location/StandardizedAddress/CountryName", doc);
        String locationPlaceCode = xpath.evaluate("//Location/StandardizedAddress/LocationPlaceCode", doc);
        String pOBoxIndicator = xpath.evaluate("//Location/StandardizedAddress/POBoxIndicator", doc);
        
        StandardizedAddress standardizedAddress = new StandardizedAddress();
        
        //////////////////////////////////////////////////////
        List<TaxingJurisdiction> taxingJurisdictions = new ArrayList<TaxingJurisdiction>();
        
        NodeList jurisdictions = (NodeList) xpath.evaluate(
            "//Location/TaxingJurisdiction",
            doc,
            XPathConstants.NODESET
        );
        System.out.println("jurisdictions :::: " + jurisdictions);

        for (int i = 0; i < jurisdictions.getLength(); i++) {
            Node jurisdiction = jurisdictions.item(i);

            String typeName = xpath.evaluate("TaxingJurisdictionTypeName", jurisdiction);
            String code = xpath.evaluate("TaxingJurisdictionCode", jurisdiction);
            String name = xpath.evaluate("TaxingJurisdictionName", jurisdiction); // may be empty
            
            TaxingJurisdiction taxingJurisdiction = new TaxingJurisdiction();
            taxingJurisdiction.setTaxingJurisdictionCode(code);
            taxingJurisdiction.setTaxingJurisdictionName(name);
            taxingJurisdiction.setTaxingJurisdictionTypeName(typeName);
            
            taxingJurisdictions.add(taxingJurisdiction);
        }

        
        //////////////////////////////////////////////////////
        
        Location location = new Location();
        location.setLocationInformation(locationInformation);
        location.setStandardizedAddress(standardizedAddress);
        location.setTaxingJurisdiction(taxingJurisdictions);
        
		//////////////////////////////////////////////////////
		String statusCode = xpath.evaluate("//StatusInformation/StatusCode", doc);
		
		System.out.println("StatusCode: " + statusCode);
		
		StatusInformation statusInformation = new StatusInformation();
		statusInformation.setStatusCode(statusCode);
		
		//////////////////////////////////////////////////////
        LocationListReply reply = new LocationListReply();
        List<Location> locations = new ArrayList<Location>();
        locations.add(location);
        
    	reply.setLocation(locations); //location to locationx5
    	reply.setStatusInformation(statusInformation);
        
        //return location;
    	return reply;
    }
}
