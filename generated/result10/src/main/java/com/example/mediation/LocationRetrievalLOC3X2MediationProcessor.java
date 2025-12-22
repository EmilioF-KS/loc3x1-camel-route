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
        String stage = exchange.getIn().getHeader("Mediation-Stage", String.class);
        String correlationId = exchange.getExchangeId();
        log.info("[{}] LOC3X2 mediation stage={} status={}", correlationId, stage, exchange.getIn().getHeader("CamelHttpResponseCode"));
    }
}

