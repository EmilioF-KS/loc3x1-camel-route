package com.example.location.processor;

import com.example.location.model.LocationRequest;
import org.apache.camel.*;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class ApplyCRP10X1EnrichmentProcessor implements Processor {
	public void process(Exchange ex) {
		if (Boolean.TRUE.equals(ex.getMessage().getHeader("call.crp10x1", Boolean.class))) {
			Map country = ex.getMessage().getBody(Map.class);
			LocationRequest r = ex.getProperty("currentRequest", LocationRequest.class);
			if (country != null) {
				Object code = country.get("countryCode"), abbr = country.get("countryAbbreviation");
				if (code != null)
					r.setCountryCode(String.valueOf(code));
				if (abbr != null)
					r.setCountryAbbreviation(String.valueOf(abbr));
			}
			ex.getMessage().setBody(r);
		}
	}
}