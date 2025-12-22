package com.example.mediation;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MediationRoutes extends RouteBuilder {
  @Override
  public void configure() throws Exception {
    onException(Exception.class)
      .handled(true)
      .bean("com.example.mfc.MfcProcessor", "buildSimpleFault")
      .setHeader("Content-Type", constant("application/xml"));
    from("direct:mediation/identity").to("xslt:classpath:xslt/identity.xsl");
  }
}
