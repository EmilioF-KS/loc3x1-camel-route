
package com.example.location.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MapXsltRoutes extends RouteBuilder {
  @Override
  public void configure() {
    from("direct:randToCimLocationXslt")
      .routeId("randToCimLocationXslt")
      .to("xslt:classpath:xslt/rand-to-cim-location.xsl");

    from("direct:loc3x1GetLocationListRequestXslt")
      .routeId("loc3x1GetLocationListRequestXslt")
      .to("xslt:classpath:xslt/loc3x1-get-location-list-request.xsl");
  }
}
