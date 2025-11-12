
package com.example.location.processor;

import com.example.location.model.LocationRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ApplyCRP10X1EnrichmentProcessor implements Processor {
    @Override
    public void process(Exchange exchange) {
        Boolean called = exchange.getMessage().getHeader("call.crp10x1", Boolean.class);
        if (Boolean.TRUE.equals(called)) {
            @SuppressWarnings("unchecked") Map<String, Object> country = exchange.getMessage().getBody(Map.class);
            LocationRequest req = exchange.getProperty("currentRequest", LocationRequest.class);
            if (country != null) {
                Object code = country.get("countryCode");
                Object abbr = country.get("countryAbbreviation");
                if (code != null) req.setCountryCode(String.valueOf(code));
                if (abbr != null) req.setCountryAbbreviation(String.valueOf(abbr));
            }
            exchange.getMessage().setBody(req);
        }
    }
}
