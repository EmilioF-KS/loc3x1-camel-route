package com.example.mediation;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MediationRoutes extends RouteBuilder {
  @Override
  public void configure() throws Exception {
    from("direct:mediation/identity").to("xslt:classpath:xslt/identity.xsl");
  }
}
