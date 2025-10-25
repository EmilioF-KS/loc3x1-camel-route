package com.ei.camel.orchestration;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.UseAdviceWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.annotation.DirtiesContext;

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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class GetLocationWithTaxingJurisdictions3X1BRouteErrorTest {

    @Autowired
    CamelContext camelContext;

    @Autowired
    ProducerTemplate template;

    @BeforeEach
    void adviceRouteForError() throws Exception {
        // Replace from with direct:start and force an exception before outbound call
        try {
            AdviceWith.adviceWith(camelContext, "getlocationwithtaxingjurisdictions3x1b-route", advice -> {
                advice.replaceFromWith("direct:start");
                advice.weaveByToUri("direct:mapLocationRetrievalLOC3X1BRequest").replace().throwException(new RuntimeException("boom"));
            });
        } catch (IllegalArgumentException ignored) {
        }

        // Start Camel context and route after advice
        camelContext.start();
        camelContext.getRouteController().startRoute("getlocationwithtaxingjurisdictions3x1b-route");
    }

    @Test
    void routeGeneratesSimpleFaultOnException() {
        String request = "<req/>";
        String result = template.requestBody("direct:start", request, String.class);
        assertNotNull(result);
        assertTrue(result.contains("\"fault\":"), "Fault JSON should be produced on errors");
    }
}