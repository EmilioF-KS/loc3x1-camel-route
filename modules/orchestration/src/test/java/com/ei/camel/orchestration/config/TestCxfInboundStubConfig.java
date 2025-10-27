package com.ei.camel.orchestration.config;

import org.apache.camel.CamelContext;
import org.apache.camel.component.cxf.jaxws.CxfEndpoint;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Provides a lightweight CXF inbound endpoint bean for tests so that
 * route creation succeeds even when we replace the from endpoint via AdviceWith.
 * The endpoint uses PAYLOAD mode and a neutral service class to avoid server init.
 */
@TestConfiguration
public class TestCxfInboundStubConfig {

    @Bean(name = "locationretrievalloc3x1bEndpoint")
    public CxfEndpoint stubInbound(CamelContext camelContext) {
        CxfEndpoint endpoint = new CxfEndpoint();
        endpoint.setCamelContext(camelContext);
        endpoint.setDataFormat(org.apache.camel.component.cxf.common.DataFormat.PAYLOAD);
        endpoint.setServiceClass(Object.class);
        // No address set to avoid creating a server; route replaces from with direct:start in tests
        return endpoint;
    }
}