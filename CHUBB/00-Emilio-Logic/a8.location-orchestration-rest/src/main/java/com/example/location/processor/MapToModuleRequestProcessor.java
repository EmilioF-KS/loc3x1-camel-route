package com.example.location.processor;

import com.example.location.model.LocationRequest;
import org.apache.camel.*;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class MapToModuleRequestProcessor implements Processor {
	public void process(Exchange ex) {
		LocationRequest r = ex.getMessage().getBody(LocationRequest.class);
		Map<String, Object> m = new HashMap<>();
		m.put("AddressLine1", r.getAddressLine1());
		m.put("AddressLine2", r.getAddressLine2());
		m.put("CityName", r.getCityName());
		m.put("StateOrProvinceCode", r.getStateOrProvinceCode());
		m.put("PostalStateAbbreviation", r.getPostalStateAbbreviation());
		m.put("PostalCode", r.getPostalCode());
		m.put("LocationPlaceCode", r.getLocationPlaceCode());
		m.put("CountryCode", r.getCountryCode());
		m.put("CountryAbbreviation", r.getCountryAbbreviation());
		ex.getMessage().setBody(m);
	}
}