
package com.example.location.processor;

import com.example.location.model.LocationRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class EnrichCountryFromCRP10X1Processor implements Processor {
    @Override
    public void process(Exchange exchange) {
        LocationRequest req = exchange.getMessage().getBody(LocationRequest.class);
        boolean hasCountryCode = isValued(req.getCountryCode());
        boolean hasCountryAbbrev = isValued(req.getCountryAbbreviation());
        boolean needsCall = !(hasCountryCode && hasCountryAbbrev) && (hasCountryCode || hasCountryAbbrev);
        exchange.getMessage().setHeader("call.crp10x1", needsCall);
    }
    private boolean isValued(String s) { return s != null && !s.isBlank(); }
}
