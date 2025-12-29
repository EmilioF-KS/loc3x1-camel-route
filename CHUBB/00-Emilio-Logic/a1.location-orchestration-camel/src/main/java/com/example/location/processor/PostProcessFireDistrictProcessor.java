
package com.example.location.processor;

import com.example.location.model.*;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class PostProcessFireDistrictProcessor implements Processor {
    @Override
    public void process(Exchange exchange) {
        LocationRequest req = exchange.getProperty("currentRequest", LocationRequest.class);
        LocationListReply reply = exchange.getMessage().getBody(LocationListReply.class);
        boolean hasAddress1 = req != null && req.getAddressLine1() != null && !req.getAddressLine1().isBlank();
        if (hasAddress1 || reply == null) return; // if AddressLine1 present, skip

        boolean warned = false;
        for (Location loc : reply.getLocation()) {
            LocationInformation info = loc.getLocationInformation();
            if (info != null && "9999".equalsIgnoreCase(info.getFireDistrictCode())) {
                info.setFireDistrictCode("");
                warned = true;
            }
        }
        if (warned) {
            StatusInformation status = reply.getStatusInformation();
            status.getError().add(new ErrorItem(
                "EPLOC30002",
                "A valid FireDistrictCode could not be returned without Address Line 1.",
                "W",
                "LocationRetrievalLOC3X1B"
            ));
            status.setStatusCode("W");
        }
        exchange.getMessage().setBody(reply);
    }
}
