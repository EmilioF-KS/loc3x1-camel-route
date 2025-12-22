package com.example.mediation;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProviderRoutes extends RouteBuilder {
    @Override
    public void configure() {
        from("direct:provider/getLocationList")
            .routeId("provider-getLocationList")
            .setBody(simple("<ProviderReply><locations/></ProviderReply>"));
        from("direct:provider/getLocation")
            .routeId("provider-getLocation")
            .setBody(simple("<ProviderReply><location/></ProviderReply>"));
    }
}

