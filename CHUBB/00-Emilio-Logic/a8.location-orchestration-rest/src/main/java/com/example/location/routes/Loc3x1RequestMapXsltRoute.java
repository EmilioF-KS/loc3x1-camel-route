
package com.example.location.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class Loc3x1RequestMapXsltRoute extends RouteBuilder {
  @Override
  public void configure() {
    from("direct:loc3x1GetLocationListRequestMapXslt")
      .routeId("loc3x1GetLocationListRequestMapXslt")
      .to("xslt:classpath:xslt/loc3x1-get-location-list-request-map.xsl");
  }
}
