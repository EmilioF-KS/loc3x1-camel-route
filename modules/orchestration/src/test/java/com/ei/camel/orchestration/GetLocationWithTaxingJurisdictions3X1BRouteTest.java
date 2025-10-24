package com.ei.camel.orchestration;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.UseAdviceWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

import com.ei.camel.orchestration.config.CxfServiceConfig;

@CamelSpringBootTest
@UseAdviceWith
@SpringBootTest(classes = {OrchestrationApplication.class, GetLocationWithTaxingJurisdictions3X1BRoute.class, CxfServiceConfig.class})
@TestPropertySource(properties = {
    "inbound.loc3x1b.address=http://localhost:8080/services/loc3x1b",
    "clients.loc3x1b.address=http://localhost:8081/services/loc3x1b",
    "camel.springboot.auto-startup=false"
})
class GetLocationWithTaxingJurisdictions3X1BRouteTest {

    @Autowired
    CamelContext camelContext;

    @Autowired
    ProducerTemplate template;

    @BeforeEach
    void adviceRoute() throws Exception {
        // Replace external endpoints with direct endpoints for unit test
        try {
            org.apache.camel.builder.AdviceWith.adviceWith(camelContext, "getlocationwithtaxingjurisdictions3x1b-route", advice -> {
                advice.replaceFromWith("direct:start");
                advice.weaveByToUri("cxf:bean:locationretrievalloc3x1bClient*").replace().to("direct:stub-outbound");
            });
        } catch (IllegalArgumentException ignored) {
            // Route may have been advised already in a previous test run
        }

        // Add the stub outbound route
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:stub-outbound").routeId("stub-outbound-route").setBody(constant("<reply/>"));
            }
        });

        // Start Camel context and required routes after advice
        camelContext.start();
        camelContext.getRouteController().startRoute("getlocationwithtaxingjurisdictions3x1b-route");
        camelContext.getRouteController().startRoute("stub-outbound-route");
        // camelContext.getRouteController().startRoute("validateRequest-route");
        // camelContext.getRouteController().startRoute("simpleFaultHandler-route");
        // camelContext.getRouteController().startRoute("mapFinalResponse-route");
        // camelContext.getRouteController().startRoute("mapLocationRetrievalLOC3X1BRequest-route");
        // camelContext.getRouteController().startRoute("mapLocationRetrievalLOC3X1BResponse-route");
        camelContext.getRouteController().startRoute("stub-outbound-route");
    }

    @Test
    void routeProcessesRequestAndProducesFinalResponse() {
        String request = "<req/>";
        String result = template.requestBody("direct:start", request, String.class);
        assertNotNull(result, "Result should not be null");
        assertEquals("<reply/>", result, "Final response should match stubbed outbound reply");
    }


}