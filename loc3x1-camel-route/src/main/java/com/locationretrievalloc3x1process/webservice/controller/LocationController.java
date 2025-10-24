package com.locationretrievalloc3x1process.webservice.controller;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chubb.xsd.ei.location.locationlistreplyloc3x1b.LocationListReply;
import com.locationretrievalloc3x1process.webservice.model.LocationRequest;

@RestController
@RequestMapping("/location")
public class LocationController {

	@Autowired
	private ProducerTemplate producerTemplate;

    @PostMapping("/retrieveList3x1")
    public Object retrieveLocation(@RequestBody LocationRequest request) {
    	System.out.println("-> LocationController.retrieveLocation");
    	System.out.println(request);
    	
    	if (producerTemplate != null) {
        	System.out.println(producerTemplate.getCamelContext());
        	System.out.println(producerTemplate.getCamelContext().isStarted());
        	
        	if (!producerTemplate.getCamelContext().isStarted()) {
        		producerTemplate.getCamelContext().start();
        	}    		
    	}

        return producerTemplate.requestBody("direct:locationProcess", request);
    }
    
    @PostMapping("/retrieveWithTaxingJurisdictions")
    public Object retrieveLocationWithTaxingJurisdictions(@RequestBody LocationRequest request) {
    	System.out.println("-> LocationController.retrieveLocationWithTaxingJurisdictions");
    	System.out.println(request);
    	
    	if (producerTemplate != null) {
        	System.out.println(producerTemplate.getCamelContext());
        	System.out.println(producerTemplate.getCamelContext().isStarted());
        	
        	if (!producerTemplate.getCamelContext().isStarted()) {
        		producerTemplate.getCamelContext().start();
        	}    		
    	}

        return producerTemplate.requestBody("direct:locationWithTaxingJurisdictionsProcess", request);
    }
}
