package com.locationretrievalloc3x1process.webservice.controller;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chubb.converter.util.XmlParserXpathLocationRequest;
import com.chubb.xsd.ei.location.locationlistreplyloc3x1b.LocationListReply;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationretrievalloc3x1process.webservice.dto.SimpleFault;
import com.locationretrievalloc3x1process.webservice.service.RansService;
import com.locationretrievalloc3x1process.webservice.service.XmlParsingService;

@RestController
@RequestMapping("/location")
public class XmlController {

	@Autowired
	private final LocationController locationController;
	
	@Autowired
    private final XmlParsingService xmlParsingService;

	@Autowired
    private final RansService ransService;
	
    public XmlController(XmlParsingService xmlParsingService, RansService randService) {
        this.locationController = new LocationController();
		this.xmlParsingService = xmlParsingService;
		this.ransService = randService;
    }

    @PostMapping(value = "/loc3x1", produces = MediaType.APPLICATION_XML_VALUE)
    public String parseLoc3x1FromXml(@RequestBody String xml) throws Exception {
    	System.out.println("-> XmlController.parseLoc3x1FromXml");
    	//System.out.println(xml);
    	
    	//LocationRequest locationRequestRes = xmlParsingService.convertXmlToObject(xml, LocationRequest.class);
    	
    	
    	//String randXmlRes = com.locationretrievalloc3x1process.webservice.mapper.LocationListReply.getLoc3x1xmlFromRand();
    	//String resLoc3x1xmlFromRand = com.locationretrievalloc3x1process.webservice.mapper.LocationListReply.getLoc3x1xmlFromRand(randXmlRes);
    	//System.out.println("resLoc3x1xmlFromRand : " + resLoc3x1xmlFromRand);
    	
    	XmlParserXpathLocationRequest xmlParserXpathLocationRequest = new 
    			XmlParserXpathLocationRequest();
    	com.locationretrievalloc3x1process.webservice.model.LocationRequest locationRequest = xmlParserXpathLocationRequest.parseResponse(xml);
    	
    	LocationListReply retrieveLocationRes = (LocationListReply) locationController.retrieveLocation(locationRequest);
    	
    	//System.out.println(":::::::  retrieveLocationRes  :::::::");
    	//System.out.println(retrieveLocationRes);
    	
    	String jsonString = null;
    	ObjectMapper mapper = new ObjectMapper();
        try {
            jsonString = mapper.writeValueAsString(retrieveLocationRes);
            //System.out.println(jsonString);            
        } catch (Exception e) {
            e.printStackTrace();
        }    
        
        // Create a JSONObject from the JSON string
        JSONObject jsonObject = new JSONObject(jsonString);

        // Convert the JSONObject to an XML string
        String xmlString = XML.toString(jsonObject, "locationListReply"); // "root" is the root element name

        // Print the resulting XML
        //System.out.println(xmlString);
        //System.out.println();
        
        String randReplyXmlRes = ransService.getRandReplyXml(locationRequest, null);
        String randReplyXmlRes1 = randReplyXmlRes.replace("<se_1:getLocationWithTaxInfoResponse>", "<se_1:getLocationWithTaxInfoResponse xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:dto=\"http://dto.rand.chubb.com\" xmlns:se=\"wsdl.http://service.rand.chubb.com\" xmlns:se_1=\"http://service.rand.chubb.com\" xmlns:p=\"http://www.ibm.com/websphere/sibx/smo/v6.0.1\">");
        //System.out.println(":: randReplyXmlRes1 : ");
        
        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><p:ServiceMessageObject xsi:type=\"p:ServiceMessageObject\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:dto=\"http://dto.rand.chubb.com\" xmlns:se=\"wsdl.http://service.rand.chubb.com\" xmlns:se_1=\"http://service.rand.chubb.com\" xmlns:p=\"http://www.ibm.com/websphere/sibx/smo/v6.0.1/\"><body xsi:type=\"se:getLocationWithTaxInfoResponse\">";
        
        String footer = "</body></p:ServiceMessageObject>";
        
        String randReplyXmlRes2 = randReplyXmlRes1.replace(header, "").replace(footer, "");
        System.out.println(" ::::::::::::::::: randReplyXmlRes2 :::::::::::::::::");
        System.out.println(randReplyXmlRes2);
        
        String randXmlRes = com.locationretrievalloc3x1process.webservice.mapper.LocationListReply.getLoc3x1xmlFromRand(randReplyXmlRes2);
        //System.out.println(":: randXmlRes : " + randXmlRes);

        // Convert the JSONObject to an XML string
        //String xmlString = XML.toString(jsonObject);

        //System.out.println(xmlString);
    	
		//return retrieveLocationRes;
		return randXmlRes;
		//return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xmlString;
    }
    
    @PostMapping(value = "/loc3x1WithTaxingJurisdictions", produces = MediaType.APPLICATION_XML_VALUE)
    public String parseLoc3x1WithTaxingJurisdictionsFromXml(@RequestBody String xml) throws Exception {
    	System.out.println("-> XmlController.parseLoc3x1WithTaxingJurisdictionsFromXml");
    	
    	XmlParserXpathLocationRequest xmlParserXpathLocationRequest = new 
    			XmlParserXpathLocationRequest();
    	com.locationretrievalloc3x1process.webservice.model.LocationRequest locationRequest = xmlParserXpathLocationRequest.parseResponse(xml);
    	
    	//LocationListReply retrieveLocationRes = (LocationListReply) locationController.retrieveLocationWithTaxingJurisdictions(locationRequest);
    	Object retrieveLocationRes = locationController.retrieveLocationWithTaxingJurisdictions(locationRequest);
    	
    	if (retrieveLocationRes instanceof LocationListReply) {
        	String jsonString = null;
        	ObjectMapper mapper = new ObjectMapper();
            try {
                jsonString = mapper.writeValueAsString(retrieveLocationRes);
                //System.out.println(jsonString);            
            } catch (Exception e) {
                e.printStackTrace();
            }    
            
            // Create a JSONObject from the JSON string
            JSONObject jsonObject = new JSONObject(jsonString);

            // Convert the JSONObject to an XML string
            String xmlString = XML.toString(jsonObject, "GetLocationWithTaxingJurisdictionsReply"); // "root" is the root element name
            
    		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xmlString;    		
    	} else if (retrieveLocationRes instanceof SimpleFault) {
        	System.out.println("      It's a SimpleFault:");  
        	System.out.println("      " + retrieveLocationRes.toString());
        	
        	String jsonString = null;
        	ObjectMapper mapper = new ObjectMapper();
            try {
                jsonString = mapper.writeValueAsString(retrieveLocationRes);
                System.out.println(jsonString);            
            } catch (Exception e) {
                e.printStackTrace();
            }    
            
            // Create a JSONObject from the JSON string
            JSONObject jsonObject = new JSONObject(jsonString);

            // Convert the JSONObject to an XML string
            String xmlString = XML.toString(jsonObject, "simpleFault"); // "root" is the root element name
            
    		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + xmlString;    		    		
    	}
    	
    	return null;
    }
}
