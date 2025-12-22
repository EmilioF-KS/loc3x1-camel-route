package com.example.mediation;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MediationRoutes extends RouteBuilder {
  @Override
  public void configure() throws Exception {
    onException(Exception.class)
      .handled(true)
      .bean("com.example.fault.EnhancedFaultBuilder", "build")
      .setHeader("Content-Type", constant("application/xml"));
  }
}
