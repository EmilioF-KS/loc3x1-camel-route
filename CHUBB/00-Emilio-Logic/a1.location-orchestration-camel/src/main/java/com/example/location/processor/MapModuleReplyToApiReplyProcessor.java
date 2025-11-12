
package com.example.location.processor;

import com.example.location.model.*;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.*;

/** Map LOC3X1M reply into API reply structure (placeholder: pass-through fields). */
@Component
public class MapModuleReplyToApiReplyProcessor implements Processor {
    @Override
    public void process(Exchange exchange) {
        @SuppressWarnings("unchecked") Map<String, Object> moduleReply = exchange.getMessage().getBody(Map.class);
        LocationListReply reply = new LocationListReply();
        // Expect moduleReply to contain a list under key "Location"
        Object listObj = moduleReply == null ? null : moduleReply.get("Location");
        if (listObj instanceof Collection<?> locations) {
            for (Object o : locations) {
                Map<String,Object> m = (Map<String,Object>) o;
                Location loc = new Location();
                LocationInformation info = new LocationInformation();
                Object fire = m.get("FireDistrictCode");
                if (fire != null) info.setFireDistrictCode(String.valueOf(fire));
                loc.setLocationInformation(info);
                reply.getLocation().add(loc);
            }
        }
        exchange.getMessage().setBody(reply);
    }
}
