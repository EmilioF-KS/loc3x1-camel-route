package com.example.location.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class XsltMappingRoute extends RouteBuilder {
	public void configure() {
		from("direct:applyGetLocationListReplyXslt").routeId("applyGetLocationListReplyXslt")
				.to("xslt:xslt/GetLocationListLOC3X1BReplyMap.xsl");
	}
}