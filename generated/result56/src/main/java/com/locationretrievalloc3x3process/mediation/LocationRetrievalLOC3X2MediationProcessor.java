package com.locationretrievalloc3x3process.mediation;

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
    String cid = exchange.getExchangeId();
    long start = System.currentTimeMillis();
    log.info("[{}] stage={} begin", cid, stage);
    try {
      log.debug("[{}] headers={}", cid, exchange.getIn().getHeaders());
      // place for transaction boundary if configured (e.g., JMS/DB)
    } catch (Exception e) {
      log.error("[{}] mediation error: {}", cid, e.getMessage(), e);
      exchange.getMessage().setHeader("CamelHttpResponseCode", 500);
      exchange.getMessage().setHeader("Content-Type", "application/xml");
      exchange.getMessage().setBody("<Error>Internal Server Error</Error>");
    } finally {
      // resource cleanup hooks (if any resources were opened)
      long took = System.currentTimeMillis() - start;
      log.info("[{}] stage={} end took={}ms", cid, stage, took);
    }
  }
}
