package com.example.mediation.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LocationRoutes extends RouteBuilder {

  @Value("${rand.access.url}")
  private String randAccessUrl;

  @Override
  public void configure() {

    // Global exception -> build SimpleFault-style XML (akin to FaultBuilder in mediation)  [1](https://ksquaregroup-my.sharepoint.com/personal/emilio_flores_theksquaregroup_com/Documents/Microsoft%20Copilot%20Chat%20Files/LocationRetrievalLOC3X1Mediation.xml)
    onException(Exception.class)
      .handled(true)
      .log(LoggingLevel.INFO, "Log LOC3X1M Fault / Failure")
      .setHeader(Exchange.CONTENT_TYPE, constant("application/xml"))
      .setBody(simple("""
        <SimpleFault>
          <faultText>EPLOC30001E The Location Retrieval service encountered an error.</faultText>
        </SimpleFault>
        """));

    // --- REST endpoints (equivalents of 'GetLocationList3X1M' and 'GetLocationWithTaxingJurisdictions3X1M') ---
    restConfiguration()
      .component("servlet")
      .contextPath("/api")
      .bindingMode(org.apache.camel.model.rest.RestBindingMode.off);

    rest("/locations")
      .post("/list").consumes("application/xml").produces("application/xml")
        .to("direct:getLocationList")
      .post("/with-taxing").consumes("application/xml").produces("application/xml")
        .to("direct:getLocationWithTax");

    // ---- Route: Get Location List ----
    from("direct:getLocationList")
      .routeId("getLocationList3X1M")
      .log(LoggingLevel.INFO, "Start Mediation - GetLocationList3X1M")             // [1](https://ksquaregroup-my.sharepoint.com/personal/emilio_flores_theksquaregroup_com/Documents/Microsoft%20Copilot%20Chat%20Files/LocationRetrievalLOC3X1Mediation.xml)
      .log(LoggingLevel.INFO, "Log LOC3X1M Request")
      .log(LoggingLevel.DEBUG, "Request payload:\n${body}")
      // "Override endpoint to RandAccess" equivalent  [1](https://ksquaregroup-my.sharepoint.com/personal/emilio_flores_theksquaregroup_com/Documents/Microsoft%20Copilot%20Chat%20Files/LocationRetrievalLOC3X1Mediation.xml)
      .setHeader(Exchange.HTTP_METHOD, constant("POST"))
      .setHeader(Exchange.CONTENT_TYPE, constant("application/xml"))
      .setHeader(Exchange.HTTP_URI, constant(randAccessUrl))
      .to("http://dummy") // URI is resolved from HTTP_URI header
      .log(LoggingLevel.INFO, "Log Rand Reply")                                    // [1](https://ksquaregroup-my.sharepoint.com/personal/emilio_flores_theksquaregroup_com/Documents/Microsoft%20Copilot%20Chat%20Files/LocationRetrievalLOC3X1Mediation.xml)
      .log(LoggingLevel.DEBUG, "Rand Reply payload:\n${body}")
      // Replace Java-Map with XSLT  [2](https://ksquaregroup-my.sharepoint.com/personal/emilio_flores_theksquaregroup_com/Documents/Microsoft%20Copilot%20Chat%20Files/RandLocationToCimLocationMap.xml)
      .to("xslt:classpath:xslt/rand-to-cim-location.xsl")
      .log(LoggingLevel.INFO, "Log LOC3X1M Response")
      .log(LoggingLevel.DEBUG, "Transformed payload:\n${body}")
      .log(LoggingLevel.INFO, "End Mediation");                                     // [1](https://ksquaregroup-my.sharepoint.com/personal/emilio_flores_theksquaregroup_com/Documents/Microsoft%20Copilot%20Chat%20Files/LocationRetrievalLOC3X1Mediation.xml)

    // ---- Route: Get Location With Taxing Jurisdictions ----
    from("direct:getLocationWithTax")
      .routeId("getLocationWithTaxingJurisdictions3X1M")
      .log(LoggingLevel.INFO, "Start Mediation - GetLocationWithTaxingJurisdictions3X1M") // [1](https://ksquaregroup-my.sharepoint.com/personal/emilio_flores_theksquaregroup_com/Documents/Microsoft%20Copilot%20Chat%20Files/LocationRetrievalLOC3X1Mediation.xml)
      .log(LoggingLevel.INFO, "Log LOC3X1M Request")
      .log(LoggingLevel.DEBUG, "Request payload:\n${body}")
      .setHeader(Exchange.HTTP_METHOD, constant("POST"))
      .setHeader(Exchange.CONTENT_TYPE, constant("application/xml"))
      .setHeader(Exchange.HTTP_URI, constant(randAccessUrl))
      .to("http://dummy")
      .log(LoggingLevel.INFO, "Log Rand Reply")
      .log(LoggingLevel.DEBUG, "Rand Reply payload:\n${body}")
      .to("xslt:classpath:xslt/rand-to-cim-location.xsl") // reuse same canonical transform
      .log(LoggingLevel.INFO, "Log LOC3X1M Response")
      .log(LoggingLevel.DEBUG, "Transformed payload:\n${body}")
      .log(LoggingLevel.INFO, "End Mediation");                                       // [1](https://ksquaregroup-my.sharepoint.com/personal/emilio_flores_theksquaregroup_com/Documents/Microsoft%20Copilot%20Chat%20Files/LocationRetrievalLOC3X1Mediation.xml)
  }
}