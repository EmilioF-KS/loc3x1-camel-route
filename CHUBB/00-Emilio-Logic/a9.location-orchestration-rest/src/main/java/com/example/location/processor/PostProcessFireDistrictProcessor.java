package com.example.location.processor;

import com.example.location.model.*;
import org.apache.camel.*;
import org.springframework.stereotype.Component;

@Component
public class PostProcessFireDistrictProcessor implements Processor {
	public void process(Exchange ex) {
		LocationRequest r = ex.getProperty("currentRequest", LocationRequest.class);
		LocationListReply reply = ex.getMessage().getBody(LocationListReply.class);
		boolean hasAddr = r != null && r.getAddressLine1() != null && !r.getAddressLine1().isBlank();
		if (hasAddr || reply == null)
			return;
		boolean warned = false;
		for (Location loc : reply.getLocation()) {
			LocationInformation info = loc.getLocationInformation();
			if (info != null && "9999".equalsIgnoreCase(info.getFireDistrictCode())) {
				info.setFireDistrictCode("");
				warned = true;
			}
		}
		if (warned) {
			StatusInformation s = reply.getStatusInformation();
			s.getError()
					.add(new ErrorItem("EPLOC30002",
							"A valid FireDistrictCode could not be returned without Address Line 1.", "W",
							"LocationRetrievalLOC3X1B"));
			s.setStatusCode("W");
		}
		ex.getMessage().setBody(reply);
	}
}