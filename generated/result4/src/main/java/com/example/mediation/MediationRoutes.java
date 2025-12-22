package com.example.mediation;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.LoggingLevel;
import org.springframework.stereotype.Component;

@Component
public class MediationRoutes extends RouteBuilder {
  @Override
  public void configure() throws Exception {
    errorHandler(defaultErrorHandler()
      .maximumRedeliveries(3)
      .redeliveryDelay(500)
      .retryAttemptedLogLevel(LoggingLevel.WARN));
    onException(Exception.class)
      .handled(true)
      .setHeader("errorCode", constant("ERROR"))
      .setHeader("errorMessage", simple("${exception.message}"))
      .setHeader("errorDetails", simple("${exception}"))
      .bean(com.example.fault.EnhancedFaultBuilder.class, "build(${header.errorCode},${header.errorMessage},${header.errorDetails})")
      .setHeader("CamelHttpResponseCode", constant(200))
      .setHeader("Content-Type", constant("application/xml"));
  }
}
