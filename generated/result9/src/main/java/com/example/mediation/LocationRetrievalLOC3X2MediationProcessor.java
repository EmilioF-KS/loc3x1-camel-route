package com.example.mediation;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("LocationRetrievalLOC3X2MediationProcessor")
public class LocationRetrievalLOC3X2MediationProcessor implements Processor {
    private static final Logger log = LoggerFactory.getLogger(LocationRetrievalLOC3X2MediationProcessor.class);

    @Override
    public void process(Exchange exchange) {
        String correlationId = exchange.getMessage().getHeader("X-Correlation-ID", String.class);
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = exchange.getExchangeId();
            exchange.getMessage().setHeader("X-Correlation-ID", correlationId);
        }

        String stage = exchange.getMessage().getHeader("Mediation-Stage", String.class);
        String isoCode = exchange.getMessage().getHeader("isoCode", String.class);
        if ("request".equalsIgnoreCase(stage)) {
            log.info("[{}] LOC3X2 request received; ISOCountryCode={}", correlationId, isoCode);
        } else if ("response".equalsIgnoreCase(stage)) {
            exchange.getMessage().setHeader("Content-Type", "application/xml");
            Integer status = exchange.getMessage().getHeader("CamelHttpResponseCode", Integer.class);
            log.info("[{}] LOC3X2 response status={}", correlationId, status);
        } else {
            log.debug("[{}] LOC3X2 mediation stage not set", correlationId);
        }
    }
}

