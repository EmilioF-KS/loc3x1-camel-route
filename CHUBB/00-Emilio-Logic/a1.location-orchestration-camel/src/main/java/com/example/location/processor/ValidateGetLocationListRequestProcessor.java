
package com.example.location.processor;

import com.example.location.model.LocationRequest;
import com.example.location.support.ValidationException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class ValidateGetLocationListRequestProcessor implements Processor {
    @Override
    public void process(Exchange exchange) {
        LocationRequest req = exchange.getMessage().getBody(LocationRequest.class);
        boolean any = isValued(req.getPostalCode()) ||
                      isValued(req.getStateOrProvinceCode()) ||
                      isValued(req.getPostalStateAbbreviation()) ||
                      isValued(req.getLocationPlaceCode());
        if (!any) {
            throw new ValidationException("EIRV0010E Required service request data fields are missing: one of PostalCode, LocationPlaceCode, StateOrProvinceCode, PostalStateAbbreviation.");
        }
    }
    private boolean isValued(String s) { return s != null && !s.isBlank(); }
}
