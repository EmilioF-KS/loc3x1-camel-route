package com.locationretrievalloc3x1process.webservice.service;

import org.springframework.stereotype.Service;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.HttpResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.chubb.converter.util.GetStateOrProvinceResponse;
import com.chubb.converter.util.XmlParser;
import com.chubb.converter.util.XmlParserXpathGetCountry;
import com.chubb.converter.util.XmlParserXpathGetStateOrProvince;
import com.chubb.xsd.ei.core.countryx1.Country;
import com.chubb.xsd.ei.corporate.getcountryreplycrp10x1.GetCountryReply;
import com.chubb.xsd.ei.corporate.getcountryrequestcrp10x2.GetCountryRequest;
import com.locationretrievalloc3x1process.webservice.model.LocationRequest;

import io.micrometer.common.util.StringUtils;

@Service
public class EnrichmentService {
	private static final String PROTOCOL_SERVER_PORT = "http://localhost:8085";

    public boolean needsStateEnrichment(LocationRequest req) throws Exception {
    	System.out.println("2		EnrichmentService.needsStateEnrichment ");
    	
    	StringBuilder result = new StringBuilder();
    	
    	if (StringUtils.isBlank(req.getStateOrProvinceCode())) {
        	String soapEndpoint = PROTOCOL_SERVER_PORT + "/get_state_or_province_request_crp11x1";
            String soapAction = "GetStateOrProvince";

            String soapRequest = 
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
                + "<p:GetStateOrProvinceRequest xsi:type=\"p:GetStateOrProvinceRequest\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:p=\"http://ei/corporate/get_state_or_province_request_crp11x1\">\r\n"
                + "  <PostalStateAbbreviation>" + req.getPostalStateAbbreviation() + "</PostalStateAbbreviation>\r\n"
                + "</p:GetStateOrProvinceRequest>";

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
                
                XmlParserXpathGetStateOrProvince xmlParser = new XmlParserXpathGetStateOrProvince();
                String getStateOrProvinceResponse = xmlParser.parseResponse(result.toString());
                
                if (getStateOrProvinceResponse != null) {
                	req.setStateOrProvinceCode(getStateOrProvinceResponse);
                	LocationRequest location = enrichState(req);
                	System.out.println("---> Final LocationRequest after enrichState : " + location);
                }
            }
    	}

        return false; //req.getStateOrProvinceCode() == null;
    }

    public boolean needsCountryEnrichment(LocationRequest req) throws Exception {
    	System.out.println("3		EnrichmentService.needsCountryEnrichment : " + req);
    	System.out.println("req.getCountryCode() : " + req.getCountryCode());

    	StringBuilder result = new StringBuilder();
    	
    	if (StringUtils.isBlank(req.getCountryAbbreviation())) {
        	String soapEndpoint = PROTOCOL_SERVER_PORT + "/get_country_request_crp10x1";
            String soapAction = "GetCountry";

            String soapRequest = 
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
                + "<p:GetCountryRequest xsi:type=\"p:GetCountryRequest\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:p=\"http://ei/corporate/get_country_request_crp10x1\">\r\n"
                + "  <CountryCode>" + req.getCountryCode() + "</CountryCode>\r\n"
                + "</p:GetCountryRequest>";

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
                
                XmlParserXpathGetCountry xmlParser = new XmlParserXpathGetCountry();
                GetCountryReply getCountryResponse = xmlParser.parseResponse(result.toString());
                
                if (getCountryResponse.getCountry() != null) {
                	LocationRequest location = enrichCountry(req, getCountryResponse.getCountry().getCountryAbbreviation());
                	System.out.println("---> Final LocationRequest after enrichCountry : " + location);
                }
            }
    	}

        return req.getCountryCode() == null;
    }

    public LocationRequest enrichState(LocationRequest req) {
    	System.out.println("enrichState : req.getStateOrProvinceCode : " + req.getStateOrProvinceCode());
        req.setStateOrProvinceCode(req.getStateOrProvinceCode()); // mock
        return req;
    }

    public LocationRequest enrichCountry(LocationRequest req, String countryAbbreviation) {
    	System.out.println("enrichCountry : countryAbbreviation : " + countryAbbreviation);
        req.setCountryAbbreviation(countryAbbreviation); // mock
        return req;
    }
}
