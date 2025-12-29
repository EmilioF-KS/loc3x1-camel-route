package com.example.location.processor;

import com.example.location.model.LocationRequest;
import com.example.location.support.ValidationException;
import org.apache.camel.*;
import org.springframework.stereotype.Component;

@Component
public class ValidateGetLocationWithTaxingRequestProcessor implements Processor {
	public void process(Exchange ex) {
		LocationRequest r = ex.getMessage().getBody(LocationRequest.class);
		boolean postal = is(r.postalCode);
		boolean hasState = is(r.stateOrProvinceCode) || is(r.postalStateAbbreviation);
		if (!postal)
			throw new ValidationException("PostalCode is required for GetLocationWithTaxingJurisdictions.");
		if (!hasState)
			throw new ValidationException(
					"Either StateOrProvinceCode or PostalStateAbbreviation is required for GetLocationWithTaxingJurisdictions.");
	}

	boolean is(String s) {
		return s != null && !s.isBlank();
	}
}