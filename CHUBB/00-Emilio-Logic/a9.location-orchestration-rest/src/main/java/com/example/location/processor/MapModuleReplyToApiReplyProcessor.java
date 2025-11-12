package com.example.location.processor;

import com.example.location.model.*;
import org.apache.camel.*;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class MapModuleReplyToApiReplyProcessor implements Processor {
	public void process(Exchange ex) {
		Map<String, Object> module = (Map<String, Object>) ex.getMessage().getBody(Map.class);
		LocationListReply reply = new LocationListReply();
		Object list = module == null ? null : module.get("Location");
		if (list instanceof Collection<?> col) {
			for (Object o : col) {
				Map<String, Object> m = (Map<String, Object>) o;
				Location loc = new Location();
				LocationInformation info = new LocationInformation();
				Object fire = m.get("FireDistrictCode");
				if (fire != null)
					info.setFireDistrictCode(String.valueOf(fire));
				loc.setLocationInformation(info);
				reply.getLocation().add(loc);
			}
		}
		ex.getMessage().setBody(reply);
	}
}