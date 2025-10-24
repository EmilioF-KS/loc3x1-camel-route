package com.ei.camel.clients.loc3x1m.config;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * LOC3X1M Client Configuration
 * 
 * Configures CXF SOAP client for Location Retrieval LOC3X1M partner service.
 * This client will be used by Camel routes for outbound service invocations.
 * 
 * Generated as part of Phase E: Integration Builder Agent
 */
@Configuration
public class Loc3x1mClientConfig {

    @Value("${external.services.loc3x1m.url}")
    private String serviceUrl;

    @Value("${external.services.loc3x1m.timeout:30000}")
    private int timeout;

    /**
     * LOC3X1M SOAP Client Bean
     * 
     * Creates a CXF client proxy for the LocationRetrieval service.
     * This bean will be referenced by Camel routes as 'locationretrievalloc3x1bClient'
     */
    @Bean
    public Object locationretrievalloc3x1bClient(Bus bus) {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setBus(bus);
        factory.setAddress(serviceUrl);
        
        // Set service interface (will be generated from WSDL)
        // factory.setServiceClass(LocationRetrievalPortType.class);
        
        // Configure client properties
        factory.getProperties().put("connection.timeout", timeout);
        factory.getProperties().put("receive.timeout", timeout);
        factory.getProperties().put("schema-validation-enabled", true);
        
        return factory.create();
    }
    
    /**
     * Client Configuration Properties
     */
    @Bean
    public Loc3x1mClientProperties loc3x1mClientProperties() {
        Loc3x1mClientProperties properties = new Loc3x1mClientProperties();
        properties.setServiceUrl(serviceUrl);
        properties.setTimeout(timeout);
        return properties;
    }
    
    /**
     * Configuration Properties Class
     */
    public static class Loc3x1mClientProperties {
        private String serviceUrl;
        private int timeout;
        
        public String getServiceUrl() { return serviceUrl; }
        public void setServiceUrl(String serviceUrl) { this.serviceUrl = serviceUrl; }
        
        public int getTimeout() { return timeout; }
        public void setTimeout(int timeout) { this.timeout = timeout; }
    }
}