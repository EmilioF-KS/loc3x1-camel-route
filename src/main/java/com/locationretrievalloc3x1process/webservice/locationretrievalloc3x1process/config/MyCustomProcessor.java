package com.locationretrievalloc3x1process.webservice.locationretrievalloc3x1process.config;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class MyCustomProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        // Access the message body
    	System.out.println("-> process");
        String originalBody = exchange.getIn().getBody(String.class);

        if (originalBody == null) 
        	originalBody = "Hello from Camel YAML DSL";
        
        // Perform some processing, e.g., modify the body
        String modifiedBody = "Processed: " + originalBody.toUpperCase();

        // Set the modified body back to the message
        exchange.getIn().setBody(modifiedBody);

        // You can also add or modify headers, properties, etc.
        exchange.getIn().setHeader("ProcessedTimestamp", System.currentTimeMillis());
    }
}