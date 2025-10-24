package com.locationretrievalloc3x1process.webservice.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import com.chubb.converter.util.XmlParserXpathLocation;
import com.chubb.xsd.ei.location.location.Location;
import com.chubb.xsd.ei.location.locationlistreplyloc3x1b.LocationListReply;
import com.locationretrievalloc3x1process.webservice.model.LocationRequest;

@Service
public class LocationListReplyService {
    public LocationListReply getLocationListReply(LocationRequest req) throws Exception {
    	System.out.println("-> LocationListReply.getLocationListReply ");
    	System.out.println("req : " + req);
    	
    	StringBuilder result = new StringBuilder();
    	
    	//if (StringUtils.isBlank(req.getStateOrProvinceCode())) {
        	String soapEndpoint = "http://localhost:8085/location_request_loc3x1b";
            String soapAction = "LOC3X1BProcess";

            String soapRequest = 
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
                + "<p:LocationRequest xsi:type=\"p:LocationRequest\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:p=\"http://ei/location/location_request_loc3x1b\">\r\n"
                + "  <AddressLine1>" + req.getAddressLine1() + "</AddressLine1>\r\n"
                + "  <CityName>" + req.getCityName() + "</CityName>\r\n"
                + "  <PostalStateAbbreviation>" + req.getPostalStateAbbreviation() + "</PostalStateAbbreviation>\r\n"
                + "  <PostalCode>" + req.getPostalCode() + "</PostalCode>\r\n"
                + "  <CountryCode>" + req.getCountryCode() + "</CountryCode>\r\n"
                + "</p:LocationRequest>";

            HttpPost post = new HttpPost(soapEndpoint);
            post.setHeader("Content-Type", "text/xml");
            post.setHeader("SOAPAction", soapAction);
            post.setEntity(new StringEntity(soapRequest));

            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpResponse response = client.execute(post);
                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
                
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                System.out.println("LocationListReply Response ");
                System.out.println(result.toString());
                
                XmlParserXpathLocation xmlParser = new XmlParserXpathLocation();
                LocationListReply locationResponse = xmlParser.parseResponse(result.toString());
                System.out.println("--> locationResponse : " + locationResponse);
                
                return locationResponse;
            }
    		
    	//}

        //return null;
    }

}
