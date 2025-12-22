package com.example.routes;

import com.example.provider.MockLoader;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ApiRoutes extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("platform-http:/api/crp10x1/v1/get-country")
            .routeId("api-crp10x1-get-country-v1")
            .setHeader("Content-Type", constant("application/xml"))
            .process(e -> new com.example.validation.XmlSchemaValidator()
                .validateBody(e.getMessage().getBody(String.class),
                    "contracts/selected/CountryRetrievalCRP10X1/GetCountryRequest.xsd"))
            .bean(MockLoader.class, "loadClasspath('mocks/crp10x1/GetCountryReply.xml')")
            .process(e -> new com.example.validation.XmlSchemaValidator()
                .validateBody(e.getMessage().getBody(String.class),
                    "contracts/selected/CountryRetrievalCRP10X1/GetCountryReply.xsd"));

        from("platform-http:/api/crp11x1/v1/get-state-or-province")
            .routeId("api-crp11x1-get-state-v1")
            .setHeader("Content-Type", constant("application/xml"))
            .process(e -> new com.example.validation.XmlSchemaValidator()
                .validateBody(e.getMessage().getBody(String.class),
                    "contracts/selected/StateOrProvinceRetrievalCRP11X1/GetStateOrProvinceRequest.xsd"))
            .bean(MockLoader.class, "loadClasspath('mocks/crp11x1/GetStateOrProvinceListReply.xml')")
            .process(e -> new com.example.validation.XmlSchemaValidator()
                .validateBody(e.getMessage().getBody(String.class),
                    "contracts/selected/StateOrProvinceRetrievalCRP11X1/GetStateOrProvinceListReply.xsd"));

        from("platform-http:/api/loc3x1b/v1/get-location-list")
            .routeId("api-loc3x1b-get-location-list-v1")
            .setHeader("Content-Type", constant("application/xml"))
            .process(e -> new com.example.validation.XmlSchemaValidator()
                .validateBody(e.getMessage().getBody(String.class),
                    "contracts/selected/LocationRetrievalLOC3X1B/GetLocationListRequest.xsd"))
            .bean(MockLoader.class, "loadClasspath('mocks/loc3x1b/LocationListReply.xml')")
            .process(e -> new com.example.validation.XmlSchemaValidator()
                .validateBody(e.getMessage().getBody(String.class),
                    "contracts/selected/LocationRetrievalLOC3X1B/LocationListReply.xsd"));
    }
}
