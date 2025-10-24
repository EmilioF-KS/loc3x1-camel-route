package com.locationretrievalloc3x1process.webservice.locationretrievalloc3x1process.config;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.language.bean.Bean;
import org.springframework.stereotype.Component;

@Component
public class MyRouteBuilder {

	@Bean(ref="myCustomProcessor")
    private final MyCustomProcessor myCustomProcessor;

    
    public MyRouteBuilder(MyCustomProcessor myCustomProcessor) {
        this.myCustomProcessor = myCustomProcessor;
    }

    /*
    @Override
    public void configure() throws Exception {
        from("timer:myTimer?period=5000") // A timer endpoint to trigger the route
            .setBody(constant("Hello Camel!")) // Set an initial body
            .log("Original Body: ${body}") // Log the original body
            .process(myCustomProcessor) // Apply the custom processor
            .log("Processed Body: ${body}") // Log the processed body
            .to("log:myLog"); // Log to a different log endpoint
    }*/
}