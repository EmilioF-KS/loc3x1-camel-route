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
        String stage = exchange.getIn().getHeader("Mediation-Stage", String.class);
        String correlationId = exchange.getExchangeId();
        log.info("[{}] orchestration mediation stage={} status={}", correlationId, stage, exchange.getIn().getHeader("CamelHttpResponseCode"));
    }
}

