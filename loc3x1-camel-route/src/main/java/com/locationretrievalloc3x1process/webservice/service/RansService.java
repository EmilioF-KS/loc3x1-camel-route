package com.locationretrievalloc3x1process.webservice.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import com.chubb.converter.util.XmlParserXpathRand;
import com.chubb.xsd.ei.location.locationlistreplyloc3x1b.LocationListReply;
import com.locationretrievalloc3x1process.webservice.model.LocationRequest;

@Service
public class RansService {
    public LocationListReply getRandReply(LocationRequest req, List<String> reqAttributes) throws Exception {
    	System.out.println("5		RansService.getRandReply ");
    	//System.out.println("req : " + req);
    	//System.out.println("reqAttributes : " + reqAttributes);
    	
    	StringBuilder result = new StringBuilder();
    	
    	String soapEndpoint = "http://localhost:8085/getLocationWithTaxInfoRequest";
        String soapAction = "GetLocationWithTaxInfo";

        String soapRequest = 
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
            + "<p:ServiceMessageObject xsi:type=\"p:ServiceMessageObject\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:dto=\"http://dto.rand.chubb.com\" xmlns:se=\"wsdl.http://service.rand.chubb.com\" xmlns:se_1=\"http://service.rand.chubb.com\" xmlns:p=\"http://www.ibm.com/websphere/sibx/smo/v6.0.1\">\r\n"
            + "   <body xsi:type=\"se:getLocationWithTaxInfoRequest\">\r\n"
            + "    <se_1:getLocationWithTaxInfo>\r\n"
            + "      <address>\r\n"
            + "        <dto:addressLine1>" + req.getAddressLine1() + "</dto:addressLine1>\r\n"
            + "        <dto:cityName>"+ req.getCityName() + "</dto:cityName>\r\n"
            + "        <dto:stateOrProvinceCode>" + req.getStateOrProvinceCode() + "</dto:stateOrProvinceCode>\r\n"
            + "        <dto:countryCode>" + req.getCountryCode() + "</dto:countryCode>\r\n"
            + "        <dto:postalCode>" + req.getPostalCode() + "</dto:postalCode>\r\n"
            + "      </address>\r\n"
            + "    </se_1:getLocationWithTaxInfo>\r\n"
            + "  </body>\r\n"
            + "</p:ServiceMessageObject>";

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
            System.out.println(":::::::: getRandReply Response ");
            System.out.println(result.toString());
               
            XmlParserXpathRand xmlParser = new XmlParserXpathRand();
            LocationListReply getRandReply = xmlParser.parseResponse(result.toString());
              
            return getRandReply;
        }
    	
        //return null;
    }
    
    public String getRandReplyXml(LocationRequest req, List<String> reqAttributes) throws Exception {
    	System.out.println("5.1		getRandReplyXml.getRandReply ");
    	//System.out.println("req : " + req);
    	//System.out.println("reqAttributes : " + reqAttributes);
    	
    	StringBuilder result = new StringBuilder();
    	
    	String soapEndpoint = "http://localhost:8085/getLocationWithTaxInfoRequest";
        String soapAction = "GetLocationWithTaxInfo";

        String soapRequest = 
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
            + "<p:ServiceMessageObject xsi:type=\"p:ServiceMessageObject\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:dto=\"http://dto.rand.chubb.com\" xmlns:se=\"wsdl.http://service.rand.chubb.com\" xmlns:se_1=\"http://service.rand.chubb.com\" xmlns:p=\"http://www.ibm.com/websphere/sibx/smo/v6.0.1\">\r\n"
            + "   <body xsi:type=\"se:getLocationWithTaxInfoRequest\">\r\n"
            + "    <se_1:getLocationWithTaxInfo>\r\n"
            + "      <address>\r\n"
            + "        <dto:addressLine1>" + req.getAddressLine1() + "</dto:addressLine1>\r\n"
            + "        <dto:cityName>"+ req.getCityName() + "</dto:cityName>\r\n"
            + "        <dto:stateOrProvinceCode>" + req.getStateOrProvinceCode() + "</dto:stateOrProvinceCode>\r\n"
            + "        <dto:countryCode>" + req.getCountryCode() + "</dto:countryCode>\r\n"
            + "        <dto:postalCode>" + req.getPostalCode() + "</dto:postalCode>\r\n"
            + "      </address>\r\n"
            + "    </se_1:getLocationWithTaxInfo>\r\n"
            + "  </body>\r\n"
            + "</p:ServiceMessageObject>";

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
            //System.out.println(":::::::: getRandReply Response ");
            //System.out.println(result.toString());
               
            //XmlParserXpathRand xmlParser = new XmlParserXpathRand();
            //LocationListReply getRandReply = xmlParser.parseResponse(result.toString());
              
            return result.toString();
        }
    	
        //return null;
    }

}
