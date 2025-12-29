
package com.example.location.processor;

import com.example.location.model.LocationRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/** Build the LOC3X1M request map (field-by-field copy). */
@Component
public class MapToModuleRequestProcessor implements Processor {
    @Override
    public void process(Exchange exchange) {
        LocationRequest req = exchange.getMessage().getBody(LocationRequest.class);
        Map<String, Object> moduleReq = new HashMap<>();
        moduleReq.put("AddressLine1", req.getAddressLine1());
        moduleReq.put("AddressLine2", req.getAddressLine2());
        moduleReq.put("CityName", req.getCityName());
        moduleReq.put("StateOrProvinceCode", req.getStateOrProvinceCode());
        moduleReq.put("PostalStateAbbreviation", req.getPostalStateAbbreviation());
        moduleReq.put("PostalCode", req.getPostalCode());
        moduleReq.put("LocationPlaceCode", req.getLocationPlaceCode());
        moduleReq.put("CountryCode", req.getCountryCode());
        moduleReq.put("CountryAbbreviation", req.getCountryAbbreviation());
        exchange.getMessage().setBody(moduleReq);
    }
}
