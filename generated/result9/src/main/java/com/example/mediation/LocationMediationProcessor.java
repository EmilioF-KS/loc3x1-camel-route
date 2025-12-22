package com.example.mediation;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("locationMediationProcessor")
public class LocationMediationProcessor implements Processor {
    private static final Logger log = LoggerFactory.getLogger(LocationMediationProcessor.class);

    @Override
    public void process(Exchange exchange) {
        String correlationId = exchange.getMessage().getHeader("X-Correlation-ID", String.class);
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = exchange.getExchangeId();
            exchange.getMessage().setHeader("X-Correlation-ID", correlationId);
        }

        String stage = exchange.getMessage().getHeader("Mediation-Stage", String.class);
        String body = exchange.getMessage().getBody(String.class);
        if (body == null) body = "";

        String trimmed = body.trim();
        if ("request".equalsIgnoreCase(stage)) {
            if (trimmed.isEmpty()) {
                log.info("[{}] Request received: empty body", correlationId);
            } else {
                log.info("[{}] Request received", correlationId);
            }
        } else if ("response".equalsIgnoreCase(stage)) {
            exchange.getMessage().setHeader("Content-Type", "application/xml");
            log.info("[{}] Response after mediation", correlationId);
        } else {
            log.debug("[{}] Mediation stage not set", correlationId);
        }
    }
}

