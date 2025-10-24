package com.ei.camel.orchestration.config;

import org.apache.camel.component.cxf.jaxws.CxfEndpoint;
import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ei.camel.orchestration.service.LocationRetrievalLOC3X1B;

@Configuration
public class CxfServiceConfig {

    @Value("${inbound.loc3x1b.address}")
    private String inboundLoc3x1bAddress;

    @Value("${clients.loc3x1b.address}")
    private String clientsLoc3x1bAddress;

    @Bean(name = "locationretrievalloc3x1bEndpoint")
    public CxfEndpoint loc3x1bInboundEndpoint(CamelContext camelContext) {
        CxfEndpoint endpoint = new CxfEndpoint();
        endpoint.setCamelContext(camelContext);
        endpoint.setAddress(inboundLoc3x1bAddress);
        endpoint.setServiceClass(LocationRetrievalLOC3X1B.class);
        return endpoint;
    }

    @Bean(name = "locationretrievalloc3x1bClient")
    public CxfEndpoint loc3x1bClientEndpoint(CamelContext camelContext) {
        CxfEndpoint endpoint = new CxfEndpoint();
        endpoint.setCamelContext(camelContext);
        endpoint.setAddress(clientsLoc3x1bAddress);
        endpoint.setServiceClass(LocationRetrievalLOC3X1B.class);
        return endpoint;
    }
}