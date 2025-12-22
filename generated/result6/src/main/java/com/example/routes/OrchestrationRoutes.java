package com.example.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrchestrationRoutes extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("platform-http:/api/orchestration/v1/get-location-list")
            .routeId("orchestration-get-location-list-v1")
            .setHeader("Content-Type", constant("application/xml"))
            .process(exchange -> {
                String xml = exchange.getMessage().getBody(String.class);
                String ns = "http://ei/location/get_location_list_request_loc3x1b";
                String b = normalize(xml, ns);
                exchange.getMessage().setBody(b);
            })
            .process(e -> new com.example.validation.XmlSchemaValidator()
                .validateBody(e.getMessage().getBody(String.class),
                    "contracts/selected/LocationRetrievalLOC3X1B/GetLocationListRequest.xsd"))
            .setProperty("inputBody", body())

            .process(exchange -> {
                String xml = exchange.getMessage().getBody(String.class);
                String code = extract(xml, "StateOrProvinceCode");
                String req = "<GetStateOrProvinceRequest xmlns=\"http://ei/corporate/get_state_or_province_request_crp11x1\">" +
                        (code != null ? "<StateOrProvinceCode>" + code + "</StateOrProvinceCode>" : "") +
                        "</GetStateOrProvinceRequest>";
                exchange.getMessage().setBody(req);
            })
            .setHeader("Content-Type", constant("application/xml"))
            .removeHeaders("CamelHttp*")
            .to("http://{{providers.mock.baseUrl}}/api/crp11x1/v1/get-state-or-province?bridgeEndpoint=true")
            .process(e -> new com.example.validation.XmlSchemaValidator()
                .validateBody(e.getMessage().getBody(String.class),
                    "contracts/selected/StateOrProvinceRetrievalCRP11X1/GetStateOrProvinceListReply.xsd"))
            .setProperty("stateReply", body())

            .setBody(exchangeProperty("inputBody"))
            .process(exchange -> {
                String xml = exchange.getMessage().getBody(String.class);
                String code = extract(xml, "CountryCode");
                String req = "<GetCountryRequest xmlns=\"http://ei/corporate/get_country_request_crp10x1\">" +
                        (code != null ? "<CountryCode>" + code + "</CountryCode>" : "") +
                        "</GetCountryRequest>";
                exchange.getMessage().setBody(req);
            })
            .setHeader("Content-Type", constant("application/xml"))
            .removeHeaders("CamelHttp*")
            .to("http://{{providers.mock.baseUrl}}/api/crp10x1/v1/get-country?bridgeEndpoint=true")
            .process(e -> new com.example.validation.XmlSchemaValidator()
                .validateBody(e.getMessage().getBody(String.class),
                    "contracts/selected/CountryRetrievalCRP10X1/GetCountryReply.xsd"))
            .setProperty("countryReply", body())

            .setBody(exchangeProperty("inputBody"))
            .setHeader("Content-Type", constant("application/xml"))
            .removeHeaders("CamelHttp*")
            .to("http://{{providers.mock.baseUrl}}/api/loc3x1b/v1/get-location-list?bridgeEndpoint=true")
            .process(e -> new com.example.validation.XmlSchemaValidator()
                .validateBody(e.getMessage().getBody(String.class),
                    "contracts/selected/LocationRetrievalLOC3X1B/LocationListReply.xsd"));
    }

    private String extract(String xml, String tag) {
        int s = xml.indexOf("<" + tag + ">");
        int e = xml.indexOf("</" + tag + ">");
        if (s == -1 || e == -1 || e < s) return null;
        int start = s + tag.length() + 2;
        return xml.substring(start, e);
    }

    private String normalize(String xml, String ns) {
        String addressLine1 = extract(xml, "AddressLine1");
        String addressLine2 = extract(xml, "AddressLine2");
        String cityName = extract(xml, "CityName");
        String stateOrProvinceCode = extract(xml, "StateOrProvinceCode");
        String postalStateAbbreviation = extract(xml, "PostalStateAbbreviation");
        String postalCode = extract(xml, "PostalCode");
        String locationPlaceCode = extract(xml, "LocationPlaceCode");
        String countryCode = extract(xml, "CountryCode");
        String countryAbbreviation = extract(xml, "CountryAbbreviation");

        StringBuilder sb = new StringBuilder();
        sb.append("<GetLocationListRequest xmlns=\"").append(ns).append("\">");
        if (addressLine1 != null) sb.append("<AddressLine1>").append(addressLine1).append("</AddressLine1>");
        if (addressLine2 != null) sb.append("<AddressLine2>").append(addressLine2).append("</AddressLine2>");
        if (cityName != null) sb.append("<CityName>").append(cityName).append("</CityName>");
        if (stateOrProvinceCode != null) sb.append("<StateOrProvinceCode>").append(stateOrProvinceCode).append("</StateOrProvinceCode>");
        if (postalStateAbbreviation != null) sb.append("<PostalStateAbbreviation>").append(postalStateAbbreviation).append("</PostalStateAbbreviation>");
        if (postalCode != null) sb.append("<PostalCode>").append(postalCode).append("</PostalCode>");
        if (locationPlaceCode != null) sb.append("<LocationPlaceCode>").append(locationPlaceCode).append("</LocationPlaceCode>");
        if (countryCode != null) sb.append("<CountryCode>").append(countryCode).append("</CountryCode>");
        if (countryAbbreviation != null) sb.append("<CountryAbbreviation>").append(countryAbbreviation).append("</CountryAbbreviation>");
        sb.append("</GetLocationListRequest>");
        return sb.toString();
    }
}
