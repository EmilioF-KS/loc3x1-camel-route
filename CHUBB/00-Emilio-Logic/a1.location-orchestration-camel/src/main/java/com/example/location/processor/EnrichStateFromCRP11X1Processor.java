
package com.example.location.processor;

import com.example.location.model.LocationRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

/**
 * If state/country information is incomplete, prepare request for CRP11X1 and route to the client.
 * This processor just decides whether to call; the actual call happens via a dynamic to() in the route.
 */
@Component
public class EnrichStateFromCRP11X1Processor implements Processor {
    @Override
    public void process(Exchange exchange) {
        LocationRequest req = exchange.getMessage().getBody(LocationRequest.class);
        boolean hasStateCode = isValued(req.getStateOrProvinceCode());
        boolean hasStateAbbrev = isValued(req.getPostalStateAbbreviation());
        boolean hasCountryCode = isValued(req.getCountryCode());
        boolean needsCall = !(hasStateCode && hasStateAbbrev && hasCountryCode) && (hasStateCode || hasStateAbbrev);
        exchange.getMessage().setHeader("call.crp11x1", needsCall);
        exchange.getMessage().setHeader("crp11x1.byCode", hasStateCode);
    }
    private boolean isValued(String s) { return s != null && !s.isBlank(); }
}
