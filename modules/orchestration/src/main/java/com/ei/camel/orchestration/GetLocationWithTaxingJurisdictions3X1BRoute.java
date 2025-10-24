package com.ei.camel.orchestration;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.apache.camel.component.cxf.common.message.CxfConstants;

/**
 * Camel route for GetLocationWithTaxingJurisdictions3X1B
 * Generated from orchestration/spec.yaml
 * 
 * Mediation flow: Inbound -> LocationRetrievalLOC3X1B
 */
@Component
public class GetLocationWithTaxingJurisdictions3X1BRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
        // Inbound endpoint for GetLocationWithTaxingJurisdictions3X1B
        from("cxf:bean:locationretrievalloc3x1bEndpoint")
            .routeId("getlocationwithtaxingjurisdictions3x1b-route")
            .log("Processing GetLocationWithTaxingJurisdictions3X1B request: ${body}")
            
            // Error handling
            .onException(Exception.class)
                .handled(true)
                .log("Error in GetLocationWithTaxingJurisdictions3X1B: ${exception.message}")
                .to("direct:simpleFaultHandler")
            .end()
            
            // Request validation
            .to("direct:validateRequest")
            
            // Step 1: Invoke LocationRetrievalLOC3X1B.GetLocationWithTaxingJurisdictions
            .to("direct:mapLocationRetrievalLOC3X1BRequest")
            .setHeader(CxfConstants.OPERATION_NAME, constant("GetLocationWithTaxingJurisdictions"))
            .to("cxf:bean:locationretrievalloc3x1bClient")
            .to("direct:mapLocationRetrievalLOC3X1BResponse")

            
            // Final response mapping
            .to("direct:mapFinalResponse")
            .log("Completed GetLocationWithTaxingJurisdictions3X1B processing: ${body}");
            
        // Request validation route
        from("direct:validateRequest")
            .log("Validating request structure");
            
        // Simple fault handler
        from("direct:simpleFaultHandler")
            .log("Generating SimpleFault response")
            .setBody(constant("{\"fault\": \"Processing error occurred\"}"));
            
        // Final response mapping
        from("direct:mapFinalResponse")
            .log("Mapping final response");

        // Stub mapping routes for partner invocations
        from("direct:mapLocationRetrievalLOC3X1BRequest")
            .log("Mapping LocationRetrievalLOC3X1B request");
        from("direct:mapLocationRetrievalLOC3X1BResponse")
            .log("Mapping LocationRetrievalLOC3X1B response");
    }
}
