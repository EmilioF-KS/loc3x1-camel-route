package com.ei.camel.orchestration;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.apache.camel.component.cxf.common.message.CxfConstants;

/**
 * Camel route for GetLocationList3X1B
 * Generated from orchestration/spec.yaml
 * 
 * Mediation flow: Inbound -> StateOrProvinceRetrievalCRP11X1Partner -> CountryRetrievalCRP10X1Partner -> LocationRetrievalLOC3X1MPartner
 */
@Component
public class GetLocationList3X1BRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
        // Inbound endpoint for GetLocationList3X1B
        from("cxf:bean:locationretrievalloc3x1bEndpoint")
            .routeId("getlocationlist3x1b-route")
            .log("Processing GetLocationList3X1B request: ${body}")
            
            // Error handling
            .onException(Exception.class)
                .handled(true)
                .log("Error in GetLocationList3X1B: ${exception.message}")
                .to("direct:simpleFaultHandler")
            .end()
            
            // Request validation
            .to("direct:validateRequest")
            
            // Step 1: Invoke StateOrProvinceRetrievalCRP11X1Partner.GetStateOrProvince
            .to("direct:mapStateOrProvinceRetrievalCRP11X1PartnerRequest")
            .setHeader(CxfConstants.OPERATION_NAME, constant("GetStateOrProvince"))
            .to("cxf:bean:stateorprovinceretrievalcrp11x1partnerClient")
            .to("direct:mapStateOrProvinceRetrievalCRP11X1PartnerResponse")
            // Step 2: Invoke CountryRetrievalCRP10X1Partner.GetCountry
            .to("direct:mapCountryRetrievalCRP10X1PartnerRequest")
            .setHeader(CxfConstants.OPERATION_NAME, constant("GetCountry"))
            .to("cxf:bean:countryretrievalcrp10x1partnerClient")
            .to("direct:mapCountryRetrievalCRP10X1PartnerResponse")
            // Step 3: Invoke LocationRetrievalLOC3X1MPartner.GetLocationList3X1M
            .to("direct:mapLocationRetrievalLOC3X1MPartnerRequest")
            .setHeader(CxfConstants.OPERATION_NAME, constant("GetLocationList3X1M"))
            .to("cxf:bean:locationretrievalloc3x1mpartnerClient")
            .to("direct:mapLocationRetrievalLOC3X1MPartnerResponse")

            
            // Final response mapping
            .to("direct:mapFinalResponse")
            .log("Completed GetLocationList3X1B processing: ${body}");
            
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
        from("direct:mapStateOrProvinceRetrievalCRP11X1PartnerRequest").log("Mapping StateOrProvince request");
        from("direct:mapStateOrProvinceRetrievalCRP11X1PartnerResponse").log("Mapping StateOrProvince response");
        from("direct:mapCountryRetrievalCRP10X1PartnerRequest").log("Mapping Country request");
        from("direct:mapCountryRetrievalCRP10X1PartnerResponse").log("Mapping Country response");
        from("direct:mapLocationRetrievalLOC3X1MPartnerRequest").log("Mapping LOC3X1M request");
        from("direct:mapLocationRetrievalLOC3X1MPartnerResponse").log("Mapping LOC3X1M response");
    }
}
