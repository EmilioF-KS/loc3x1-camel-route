package com.ei.camel.orchestration;

import java.util.concurrent.atomic.AtomicReference;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
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
class GetLocationWithTaxingJurisdictions3X1BRouteHeaderTest {

    @Autowired
    CamelContext camelContext;

    @Autowired
    ProducerTemplate template;

    private final AtomicReference<String> capturedOperationName = new AtomicReference<>();

    @BeforeEach
    void adviceRouteForHeader() throws Exception {
        try {
            AdviceWith.adviceWith(camelContext, "getlocationwithtaxingjurisdictions3x1b-route", advice -> {
                advice.replaceFromWith("direct:start");
                advice.weaveByToUri("cxf:bean:locationretrievalloc3x1bClient*").replace().to("direct:capture-outbound");
            });
        } catch (IllegalArgumentException ignored) {
        }

        // Real Camel route to capture headers (no mocks)
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:capture-outbound").routeId("capture-outbound-route")
                    .process(exchange -> {
                        String op = exchange.getIn().getHeader(CxfConstants.OPERATION_NAME, String.class);
                        capturedOperationName.set(op);
                    })
                    .setBody(constant("<reply/>"));
            }
        });

        camelContext.start();
        camelContext.getRouteController().startRoute("getlocationwithtaxingjurisdictions3x1b-route");
        camelContext.getRouteController().startRoute("capture-outbound-route");
    }

    @Test
    void routeSetsOperationNameHeaderBeforeOutbound() {
        String request = "<req/>";
        template.sendBody("direct:start", request);
        assertEquals("GetLocationWithTaxingJurisdictions", capturedOperationName.get(), "CXF operation header should be set");
    }
}