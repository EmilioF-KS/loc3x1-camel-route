
package com.example.location.processor;

import com.example.location.model.LocationRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Map;

/** Apply response from CRP11X1 into the current request. */
@Component
public class ApplyCRP11X1EnrichmentProcessor implements Processor {
    @Override
    public void process(Exchange exchange) {
        Boolean called = exchange.getMessage().getHeader("call.crp11x1", Boolean.class);
        if (Boolean.TRUE.equals(called)) {
            @SuppressWarnings("unchecked") Map<String, Object> state = exchange.getMessage().getBody(Map.class);
            LocationRequest req = exchange.getProperty("currentRequest", LocationRequest.class);
            if (state != null) {
                // Expecting fields: stateOrProvinceCode, postalStateAbbreviation, countryCode
                Object c = state.get("stateOrProvinceCode");
                Object a = state.get("postalStateAbbreviation");
                Object country = state.get("countryCode");
                if (c != null) req.setStateOrProvinceCode(String.valueOf(c));
                if (a != null) req.setPostalStateAbbreviation(String.valueOf(a));
                if (country != null) req.setCountryCode(String.valueOf(country));
            }
            exchange.getMessage().setBody(req);
        }
    }
}
