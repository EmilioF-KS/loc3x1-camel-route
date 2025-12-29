
package com.example.location.routes;

import com.example.location.model.LocationListReply;
import com.example.location.model.LocationRequest;
import com.example.location.model.SimpleFault;
import com.example.location.processor.*;
import com.example.location.support.ValidationException;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GetLocationListRoute extends RouteBuilder {

    @Value("${endpoints.crp11x1.getStateOrProvince}")
    private String crp11x1Url;

    @Value("${endpoints.crp10x1.getCountry}")
    private String crp10x1Url;

    @Value("${endpoints.loc3x1m.getLocationList}")
    private String loc3x1mUrl;

    @Override
    public void configure() {
        onException(ValidationException.class)
            .handled(true)
            .log("Validation error: ${exception.message}")
            .process(e -> e.getMessage().setBody(new SimpleFault("EIRV0010E " + e.getException().getMessage())))
            .marshal().json(JsonLibrary.Jackson);

        onException(Exception.class)
            .handled(true)
            .log("Service Processing Exception: ${exception.message}")
            .process(e -> e.getMessage().setBody(new SimpleFault("EISP0001E Service Processing Exception: " + e.getException().getMessage())))
            .marshal().json(JsonLibrary.Jackson);

        rest("/locations")
            .post("/list").consumes("application/json").produces("application/json").to("direct:getLocationList");

        from("direct:getLocationList")
            .routeId("getLocationList")
            .log("Start Process - GET_LOCATION_LIST_LOC3X1B")
            .unmarshal().json(JsonLibrary.Jackson, LocationRequest.class)
            .process(e -> e.setProperty("currentRequest", e.getMessage().getBody(LocationRequest.class)))
            .log("Log LOC3X1B Request: ${body}")
            .bean(ValidateGetLocationListRequestProcessor.class)
            .bean(EnrichStateFromCRP11X1Processor.class)
            .choice()
                .when(header("call.crp11x1").isEqualTo(true))
                    .process(e -> {
                        LocationRequest req = e.getProperty("currentRequest", LocationRequest.class);
                        if (Boolean.TRUE.equals(e.getMessage().getHeader("crp11x1.byCode", Boolean.class))) {
                            e.getMessage().setBody(java.util.Map.of("StateOrProvinceCode", req.getStateOrProvinceCode()));
                        } else {
                            e.getMessage().setBody(java.util.Map.of("PostalStateAbbreviation", req.getPostalStateAbbreviation()));
                        }
                    })
                    .marshal().json(JsonLibrary.Jackson)
                    .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                    .setHeader("CamelHttpMethod", constant("POST"))
                    .toD(crp11x1Url)
                    .unmarshal().json(JsonLibrary.Jackson, java.util.Map.class)
                    .bean(ApplyCRP11X1EnrichmentProcessor.class)
                .end()
            .bean(EnrichCountryFromCRP10X1Processor.class)
            .choice()
                .when(header("call.crp10x1").isEqualTo(true))
                    .process(e -> {
                        LocationRequest req = e.getProperty("currentRequest", LocationRequest.class);
                        java.util.Map<String,Object> payload = new java.util.HashMap<>();
                        if (req.getCountryCode() != null) payload.put("CountryCode", req.getCountryCode());
                        if (req.getCountryAbbreviation() != null) payload.put("CountryAbbreviation", req.getCountryAbbreviation());
                        e.getMessage().setBody(payload);
                    })
                    .marshal().json(JsonLibrary.Jackson)
                    .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                    .setHeader("CamelHttpMethod", constant("POST"))
                    .toD(crp10x1Url)
                    .unmarshal().json(JsonLibrary.Jackson, java.util.Map.class)
                    .bean(ApplyCRP10X1EnrichmentProcessor.class)
                .end()
            .bean(MapToModuleRequestProcessor.class)
            .marshal().json(JsonLibrary.Jackson)
            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
            .setHeader("CamelHttpMethod", constant("POST"))
            .toD(loc3x1mUrl)
            .unmarshal().json(JsonLibrary.Jackson, java.util.Map.class)
            .bean(MapModuleReplyToApiReplyProcessor.class)
            .bean(PostProcessFireDistrictProcessor.class)
            .log("Log LOC3X1B Reply: ${body}")
            .log("End Process - GET_LOCATION_LIST_LOC3X1B")
            .marshal().json(JsonLibrary.Jackson, LocationListReply.class);
    }
}
