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
        long start = System.currentTimeMillis();
        log.info("[{}] stage={} begin", correlationId, stage);
        try {
            log.debug("[{}] headers={}", correlationId, exchange.getIn().getHeaders());
            // Transaction boundary placeholder (if a transaction manager is configured)
        } catch (Exception e) {
            log.error("[{}] mediation error: {}", correlationId, e.getMessage(), e);
            exchange.getMessage().setHeader("Content-Type", "application/xml");
            exchange.getMessage().setHeader("CamelHttpResponseCode", 500);
            exchange.getMessage().setBody("<Error>Internal Server Error</Error>");
        } finally {
            long took = System.currentTimeMillis() - start;
            log.info("[{}] stage={} end took={}ms", correlationId, stage, took);
        }
    }
}
