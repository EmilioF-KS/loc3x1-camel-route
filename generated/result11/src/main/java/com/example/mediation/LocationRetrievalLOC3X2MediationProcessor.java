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
        if (stage == null) stage = "unknown";
        String body = exchange.getIn().getBody(String.class);
        log.info("[loc3x2] stage={} body={}", stage, body);
    }
}
