package com.locationretrievalloc3x3process.mediation;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MediationRoutes extends RouteBuilder {
  @Override
  public void configure() throws Exception {
    onException(Exception.class).handled(true)
      .maximumRedeliveries(3).redeliveryDelay(1000)
      .process(new Processor() {
        final Logger log = LoggerFactory.getLogger(MediationRoutes.class);
        @Override public void process(Exchange exchange) {
          log.error("Mediation error: {}", exchange.getException() != null ? exchange.getException().getMessage() : "unknown");
          exchange.getMessage().setHeader("Content-Type", "application/xml");
          exchange.getMessage().setHeader("CamelHttpResponseCode", 500);
          exchange.getMessage().setBody("<Error>Internal Server Error</Error>");
        }
      });
    from("direct:mediation/identity").to("xslt:classpath:xsl/identity.xsl");
  }
}
