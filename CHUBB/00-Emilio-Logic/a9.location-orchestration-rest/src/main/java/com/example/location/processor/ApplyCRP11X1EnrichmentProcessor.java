package com.example.location.processor;

import com.example.location.model.LocationRequest;
import org.apache.camel.*;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class ApplyCRP11X1EnrichmentProcessor implements Processor {
	public void process(Exchange ex) {
		if (Boolean.TRUE.equals(ex.getMessage().getHeader("call.crp11x1", Boolean.class))) {
			Map state = ex.getMessage().getBody(Map.class);
			LocationRequest r = ex.getProperty("currentRequest", LocationRequest.class);
			if (state != null) {
				Object c = state.get("stateOrProvinceCode"), a = state.get("postalStateAbbreviation"),
						cc = state.get("countryCode");
				if (c != null)
					r.setStateOrProvinceCode(String.valueOf(c));
				if (a != null)
					r.setPostalStateAbbreviation(String.valueOf(a));
				if (cc != null)
					r.setCountryCode(String.valueOf(cc));
			}
			ex.getMessage().setBody(r);
		}
	}
}