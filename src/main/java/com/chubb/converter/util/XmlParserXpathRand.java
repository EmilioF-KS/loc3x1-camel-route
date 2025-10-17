package com.chubb.converter.util;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import com.chubb.xsd.dto.rand.chubb.com.StatusInformation;
import com.chubb.xsd.ei.core.taxingjurisdictionx1.TaxingJurisdiction;
import com.chubb.xsd.ei.location.locationinformationx2.LocationInformation;
import com.chubb.xsd.ei.location.locationlistreplyloc3x1b.LocationListReply;
import com.chubb.xsd.ei.location.locationx5.Location;
import com.chubb.xsd.ei.location.standardizedaddressx2.StandardizedAddress;

public class XmlParserXpathRand {

    public LocationListReply parseResponse(String xml) throws Exception {
    	//System.out.println("-> XmlParserXpathRand.parseResponse");
    	//System.out.println(xml);
    	System.out.println("6		Setting Service Mediation Response to LOC3X1 Response");
    	
        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new ByteArrayInputStream(xml.getBytes()));

        XPath xpath = XPathFactory.newInstance().newXPath();

        String locationPlaceCode = xpath.evaluate("//location/locationPlaceCode", doc);
        String cityName = xpath.evaluate("//location/cityName", doc);
        String cityCode = xpath.evaluate("//location/cityCode", doc);
        String cityNameSort = xpath.evaluate("//location/cityNameSort", doc);
        String countyName = xpath.evaluate("//location/countyName", doc);
        String countyCode = xpath.evaluate("//location/countyCode", doc);
        String stateProvinceAbbreviation = xpath.evaluate("//location/stateProvinceAbbreviation", doc);
        String postalStateAbbreviation = xpath.evaluate("//location/postalStateAbbreviation", doc);
        String stateOrProvinceName = xpath.evaluate("//location/stateOrProvinceName", doc);
        String stateOrProvinceCode = xpath.evaluate("//location/stateOrProvinceCode", doc);
        String countryName = xpath.evaluate("//location/countryName", doc);
        String countryCode = xpath.evaluate("//location/countryCode", doc);
        String postalCode = xpath.evaluate("//location/postalCode", doc);
        String fireDistrictCode = xpath.evaluate("//location/fireDistrictCode", doc);
        String locationClassCode = xpath.evaluate("//location/locationClassCode", doc);
        String windstormZoneIndicator = xpath.evaluate("//location/windstormZoneIndicator", doc);
        String windstormZoneCode = xpath.evaluate("//location/windstormZoneCode", doc);
        String nextMcNallyPlaceCode = xpath.evaluate("//location/nextMcNallyPlaceCode", doc);
        String poBoxIndicator = xpath.evaluate("//location/poBoxIndicator", doc);
        String useMcNallyPlaceCode = xpath.evaluate("//location/useMcNallyPlaceCode", doc);
        String useZipCode = xpath.evaluate("//location/useZipCode", doc);
        String mcNallyIncluisonPlaceCode = xpath.evaluate("//location/mcNallyIncluisonPlaceCode", doc);
        
        String changeEffectiveDate = xpath.evaluate("//location/taxInfo/changeEffectiveDate", doc);
        String taxCode = xpath.evaluate("//location/taxInfo/taxCode", doc);
        String iriCode = xpath.evaluate("//location/taxInfo/iriCode", doc);
        String licenseCode = xpath.evaluate("//location/taxInfo/licenseCode", doc);
        String alternateTaxCode = xpath.evaluate("//location/taxInfo/alternateTaxCode", doc);
        
        List<Location> locations = new ArrayList<Location>(); 
        Location location = new Location();
        
		//////////////////////////////////////////////////////
		
        LocationListReply reply = new LocationListReply();
        
        LocationInformation locationInformation = new LocationInformation();
        locationInformation.setCityCode(cityCode);
        locationInformation.setCountyCode(countyCode);
        locationInformation.setFireDistrictCode(fireDistrictCode);
        
        location.setLocationInformation(locationInformation);
        
        TaxingJurisdiction taxingJurisdictionFireDistrict = new TaxingJurisdiction();
        TaxingJurisdiction taxingJurisdictionTaxingTerritory = new TaxingJurisdiction();
        List<TaxingJurisdiction> taxingJurisdictions = new ArrayList<TaxingJurisdiction>();
        
        taxingJurisdictionFireDistrict.setTaxingJurisdictionTypeName("FireDistrict");
        taxingJurisdictionFireDistrict.setTaxingJurisdictionCode(fireDistrictCode);
        taxingJurisdictions.add(taxingJurisdictionFireDistrict);
        
        taxingJurisdictionTaxingTerritory.setTaxingJurisdictionTypeName("TaxingTerritory");
        taxingJurisdictionTaxingTerritory.setTaxingJurisdictionName(stateOrProvinceName);
        taxingJurisdictions.add(taxingJurisdictionTaxingTerritory);
        
        location.setTaxingJurisdiction(taxingJurisdictions);
        
        StandardizedAddress standardizedAddress = new StandardizedAddress();
        standardizedAddress.setCityName(cityName);
        standardizedAddress.setCountyName(countyName);
        standardizedAddress.setStateOrProvinceCode(stateOrProvinceCode);
        standardizedAddress.setStateOrProvinceName(stateOrProvinceName);
        standardizedAddress.setPostalStateAbbreviation(postalStateAbbreviation);
        standardizedAddress.setPostalCode(postalCode);
        standardizedAddress.setCountryCode(countryCode);
        standardizedAddress.setCountryName(countryName);
        standardizedAddress.setLocationPlaceCode(locationPlaceCode);
        standardizedAddress.setPOBoxIndicator(poBoxIndicator.equalsIgnoreCase("Y") ?
        		true : false);
        
        location.setStandardizedAddress(standardizedAddress);
        
        locations.add(location);
        
    	reply.setLocation(locations); //location to locationx5
    	
    	StatusInformation statusInformation= new StatusInformation();
    	statusInformation.setStatusCode("S");
    	
    	//XSD New Version
    	//List<com.chubb.mappers.xmldto.loc3x1.response.Location> locations2 = 
    		//	new ArrayList<com.chubb.mappers.xmldto.loc3x1.response.Location>(); 
    	/*Location location2 = new Location();
        
    	GetLocationWithTaxingJurisdictionsReply reply2 = new GetLocationWithTaxingJurisdictionsReply();
        
        com.chubb.mappers.xmldto.loc3x1.response.LocationInformation locationInformation2 = 
        		new com.chubb.mappers.xmldto.loc3x1.response.LocationInformation();
        locationInformation2.setCityCode(cityCode);
        locationInformation2.setCountyCode(countyCode);
        locationInformation2.setFireDistrictCode(fireDistrictCode);
        
        location2.setLocationInformation(locationInformation2);
        
        com.chubb.mappers.xmldto.loc3x1.response.TaxingJurisdiction taxingJurisdictionFireDistrict2 = 
        		new com.chubb.mappers.xmldto.loc3x1.response.TaxingJurisdiction();
        com.chubb.mappers.xmldto.loc3x1.response.TaxingJurisdiction taxingJurisdictionTaxingTerritory2 = 
        		new com.chubb.mappers.xmldto.loc3x1.response.TaxingJurisdiction();
        List<com.chubb.mappers.xmldto.loc3x1.response.TaxingJurisdiction> taxingJurisdictions2 = 
        		new ArrayList<com.chubb.mappers.xmldto.loc3x1.response.TaxingJurisdiction>();
        
        taxingJurisdictionFireDistrict2.setTaxingJurisdictionTypeName("FireDistrict");
        taxingJurisdictionFireDistrict2.setTaxingJurisdictionCode(fireDistrictCode);
        taxingJurisdictions2.add(taxingJurisdictionFireDistrict2);
        
        taxingJurisdictionTaxingTerritory2.setTaxingJurisdictionTypeName("TaxingTerritory");
        taxingJurisdictionTaxingTerritory2.setTaxingJurisdictionName(stateOrProvinceName);
        taxingJurisdictions2.add(taxingJurisdictionTaxingTerritory2);
        
        location2.setTaxingJurisdictions(taxingJurisdictions2);
        
        com.chubb.mappers.xmldto.loc3x1.response.StandardizedAddress standardizedAddress2 = 
        		new com.chubb.mappers.xmldto.loc3x1.response.StandardizedAddress();
        standardizedAddress2.setCityName(cityName);
        standardizedAddress2.setCountyName(countyName);
        standardizedAddress2.setStateOrProvinceCode(stateOrProvinceCode);
        standardizedAddress2.setStateOrProvinceName(stateOrProvinceName);
        standardizedAddress2.setPostalStateAbbreviation(postalStateAbbreviation);
        standardizedAddress2.setPostalCode(postalCode);
        standardizedAddress2.setCountryCode(countryCode);
        standardizedAddress2.setCountryName(countryName);
        standardizedAddress2.setLocationPlaceCode(locationPlaceCode);
        standardizedAddress2.setPoBoxIndicator(new Boolean(poBoxIndicator));
        
        location2.setStandardizedAddress(standardizedAddress2);
        
        //locations2.add(location2);
        
    	reply2.setLocation(location2); //location to locationx5
    	
    	com.chubb.mappers.xmldto.loc3x1.response.StatusInformation statusInformation2 = 
    			new com.chubb.mappers.xmldto.loc3x1.response.StatusInformation();
    	statusInformation2.setStatusCode("S");
    	
    	reply2.setStatusInformation(statusInformation2);

        System.out.println("::::::::::::::::::::::::::: reply2 ::::::::::: " + reply2);
        
    	//XSD New Version
    	*/
    	reply.setStatusInformation(statusInformation);
    	
    	return reply;
    }
}
