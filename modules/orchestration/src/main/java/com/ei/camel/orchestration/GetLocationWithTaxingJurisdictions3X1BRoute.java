package com.ei.camel.orchestration;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.springframework.stereotype.Component;
import org.slf4j.MDC;

import java.time.Instant;

import com.ei.camel.orchestration.dto.SimpleFault;

@Component
public class GetLocationWithTaxingJurisdictions3X1BRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Centralized exception handling: translate to SimpleFault and audit
        onException(Exception.class)
                .handled(true)
                .to("direct:simpleFaultHandler");

        from("cxf:bean:locationretrievalloc3x1bEndpoint")
                .routeId("getlocationwithtaxingjurisdictions3x1b-route")
                // Propagate/initialize correlation ID and put into MDC
                .process(exchange -> {
                    String corr = exchange.getIn().getHeader("X-Correlation-ID", String.class);
                    if (corr == null || corr.isEmpty()) {
                        corr = exchange.getExchangeId();
                        exchange.getIn().setHeader("X-Correlation-ID", corr);
                    }
                    MDC.put("correlationId", corr);
                })
                .log("Processing GetLocationWithTaxingJurisdictions3X1B request: ${body}")
                .to("direct:validateRequest")
                .to("direct:mapLocationRetrievalLOC3X1BRequest")
                .setHeader(CxfConstants.OPERATION_NAME, constant("GetLocationWithTaxingJurisdictions"))
                .to("cxf:bean:locationretrievalloc3x1bClient")
                .to("direct:mapLocationRetrievalLOC3X1BResponse")
                .to("direct:mapFinalResponse")
                .log("Completed GetLocationWithTaxingJurisdictions3X1B processing: ${body}");

        from("direct:validateRequest")
                .log("Validating request structure");

        from("direct:simpleFaultHandler")
                .process(exchange -> {
                    Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                    String corr = exchange.getIn().getHeader("X-Correlation-ID", String.class);
                    if (corr == null || corr.isEmpty()) {
                        corr = exchange.getExchangeId();
                        exchange.getIn().setHeader("X-Correlation-ID", corr);
                    }
                    MDC.put("correlationId", corr);

                    SimpleFault fault = new SimpleFault();
                    String message = e != null && e.getMessage() != null ? e.getMessage() : "Processing error occurred";
                    fault.setFaultMessageText(message);
                    fault.setErrorCode("ERR-500");
                    fault.setSource("GetLocationWithTaxingJurisdictions3X1B");
                    fault.setCorrelationId(corr);
                    fault.setTimestamp(Instant.now().toString());

                    String safeMessage = message.replace("\"", "\\\"");
                    String safeSource = fault.getSource().replace("\"", "\\\"");
                    String safeCode = fault.getErrorCode().replace("\"", "\\\"");
                    String safeCorr = fault.getCorrelationId().replace("\"", "\\\"");
                    String safeTs = fault.getTimestamp().replace("\"", "\\\"");
                    String json = String.format("{\"fault\": {\"message\": \"%s\", \"code\": \"%s\", \"source\": \"%s\", \"correlationId\": \"%s\", \"timestamp\": \"%s\"}}", safeMessage, safeCode, safeSource, safeCorr, safeTs);

                    exchange.getMessage().setBody(json);
                    exchange.getMessage().setHeader("X-Correlation-ID", corr);
                })
                .to("direct:auditEvent");

        from("direct:auditEvent")
                .log("AUDIT fault correlationId=${header.X-Correlation-ID} body=${body}");

        from("direct:mapFinalResponse")
                .log("Mapping final response");

        from("direct:mapLocationRetrievalLOC3X1BRequest")
                .log("Mapping LocationRetrievalLOC3X1B request");
        from("direct:mapLocationRetrievalLOC3X1BResponse")
                .log("Mapping LocationRetrievalLOC3X1B response");
    }
}
