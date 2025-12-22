package com.example.provider;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProviderStubRoutes extends RouteBuilder {
    @Override
    public void configure() {
        from("direct:provider/loc3x1b")
            .routeId("provider-loc3x1b")
            .setBody(simple("<LocationListReply><Location/><StatusInformation/></LocationListReply>"));

        from("direct:provider/crp10x1")
            .routeId("provider-crp10x1")
            .setBody(simple("<GetCountryReply xmlns=\"http://ei/corporate/get_country_reply_crp10x1\"><Country xmlns=\"http://ei/core/country\"><CountryCode>US</CountryCode><CountryName>United States</CountryName></Country></GetCountryReply>"));

        from("direct:provider/crp11x1")
            .routeId("provider-crp11x1")
            .setBody(simple("<GetStateOrProvinceListReply xmlns=\"http://ei/corporate/get_state_or_province_list_reply_crp11x1\"><StateOrProvince xmlns=\"http://ei/core/state_or_province\"><StateOrProvinceCode>CA</StateOrProvinceCode><StateOrProvinceName>California</StateOrProvinceName></StateOrProvince></GetStateOrProvinceListReply>"));
    }
}
