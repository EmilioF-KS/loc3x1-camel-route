
package com.example.location.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Placeholders for external service clients. You can override the actual endpoints at runtime using
 * exchange properties (crp11x1OverrideUri, crp10x1OverrideUri, loc3x1mOverrideUri) or configure application.yml.
 */
@Component
public class ExternalClientsRoute extends RouteBuilder {
    @Override
    public void configure() {
        // These direct: endpoints are convenient stubs to plug in CXF or HTTP clients later if desired.
        // For example, replace with to("cxf://...?") to call SOAP services.

        // No default implementations here to avoid using synthetic data.
        // If invoked without a configured endpoint, they will throw an exception which is handled by the global onException.

        from("direct:crp11x1.getStateOrProvince")
            .routeId("crp11x1.getStateOrProvince")
            .throwException(new UnsupportedOperationException("Configure CRP11X1 endpoint or provide ${exchangeProperty.crp11x1OverrideUri}"));

        from("direct:crp10x1.getCountry")
            .routeId("crp10x1.getCountry")
            .throwException(new UnsupportedOperationException("Configure CRP10X1 endpoint or provide ${exchangeProperty.crp10x1OverrideUri}"));

        from("direct:loc3x1m.getLocationList")
            .routeId("loc3x1m.getLocationList")
            .throwException(new UnsupportedOperationException("Configure LOC3X1M endpoint or provide ${exchangeProperty.loc3x1mOverrideUri}"));
    }
}
