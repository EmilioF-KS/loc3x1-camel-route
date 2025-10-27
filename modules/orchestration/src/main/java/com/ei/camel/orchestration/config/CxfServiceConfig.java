package com.ei.camel.orchestration.config;

import org.apache.camel.CamelContext;
import org.apache.camel.component.cxf.jaxws.CxfEndpoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import com.ei.camel.orchestration.service.LocationRetrievalLOC3X1B;

import org.apache.camel.support.jsse.KeyManagersParameters;
import org.apache.camel.support.jsse.KeyStoreParameters;
import org.apache.camel.support.jsse.SSLContextParameters;
import org.apache.camel.support.jsse.TrustManagersParameters;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CxfServiceConfig {

    @Value("${inbound.loc3x1b.address}")
    private String inboundLoc3x1bAddress;

    @Value("${clients.loc3x1b.address}")
    private String clientsLoc3x1bAddress;

    @Value("${clients.loc3x1b.connectTimeout:5000}")
    private long clientConnectTimeoutMs;

    @Value("${clients.loc3x1b.receiveTimeout:10000}")
    private long clientReceiveTimeoutMs;

    @Value("${cxf.tls.truststore.path:}")
    private String truststorePath;

    @Value("${cxf.tls.truststore.password:}")
    private String truststorePassword;

    @Value("${cxf.tls.keystore.path:}")
    private String keystorePath;

    @Value("${cxf.tls.keystore.password:}")
    private String keystorePassword;

    @ConditionalOnProperty(name = "cxf.inbound.enabled", havingValue = "true", matchIfMissing = false)
    @Bean(name = "locationretrievalloc3x1bEndpoint")
    public CxfEndpoint loc3x1bInboundEndpoint(CamelContext camelContext) {
        CxfEndpoint endpoint = new CxfEndpoint();
        endpoint.setCamelContext(camelContext);
        endpoint.setAddress(inboundLoc3x1bAddress);
        // Use PAYLOAD mode to avoid strict JAX-WS service requirements in tests
        endpoint.setDataFormat(org.apache.camel.component.cxf.common.DataFormat.PAYLOAD);
        // Neutral service class to prevent CXF trying to build a full JAX-WS service
        endpoint.setServiceClass(Object.class);

        // Conditional TLS for inbound when using HTTPS
        if (inboundLoc3x1bAddress != null && inboundLoc3x1bAddress.startsWith("https")) {
            try {
                SSLContextParameters ssl = new SSLContextParameters();
                if (truststorePath != null && !truststorePath.isEmpty()) {
                    KeyStoreParameters tsp = new KeyStoreParameters();
                    tsp.setResource(truststorePath);
                    tsp.setPassword(truststorePassword);
                    TrustManagersParameters tmp = new TrustManagersParameters();
                    tmp.setKeyStore(tsp);
                    ssl.setTrustManagers(tmp);
                }
                if (keystorePath != null && !keystorePath.isEmpty()) {
                    KeyStoreParameters ksp = new KeyStoreParameters();
                    ksp.setResource(keystorePath);
                    ksp.setPassword(keystorePassword);
                    KeyManagersParameters kmp = new KeyManagersParameters();
                    kmp.setKeyStore(ksp);
                    kmp.setKeyPassword(keystorePassword);
                    ssl.setKeyManagers(kmp);
                }
                endpoint.setSslContextParameters(ssl);
            } catch (Exception e) {
                System.out.println("[WARN] SSLContextParameters (inbound) configuration failed: " + e.getMessage());
            }
        }

        return endpoint;
    }

    @Bean(name = "locationretrievalloc3x1bClient")
    public CxfEndpoint loc3x1bClientEndpoint(CamelContext camelContext) {
        CxfEndpoint endpoint = new CxfEndpoint();
        endpoint.setCamelContext(camelContext);
        endpoint.setAddress(clientsLoc3x1bAddress);
        endpoint.setDataFormat(org.apache.camel.component.cxf.common.DataFormat.PAYLOAD);
        endpoint.setServiceClass(Object.class);

        // Externalised timeouts (applied by CXF via endpoint properties)
        Map<String, Object> props = new HashMap<>();
        props.put("receiveTimeout", clientReceiveTimeoutMs);
        props.put("connectionTimeout", clientConnectTimeoutMs);
        endpoint.setProperties(props);

        // Conditional TLS via SSLContextParameters when using HTTPS
        if (clientsLoc3x1bAddress != null && clientsLoc3x1bAddress.startsWith("https")) {
            try {
                SSLContextParameters ssl = new SSLContextParameters();
                if (truststorePath != null && !truststorePath.isEmpty()) {
                    KeyStoreParameters tsp = new KeyStoreParameters();
                    tsp.setResource(truststorePath);
                    tsp.setPassword(truststorePassword);
                    TrustManagersParameters tmp = new TrustManagersParameters();
                    tmp.setKeyStore(tsp);
                    ssl.setTrustManagers(tmp);
                }
                if (keystorePath != null && !keystorePath.isEmpty()) {
                    KeyStoreParameters ksp = new KeyStoreParameters();
                    ksp.setResource(keystorePath);
                    ksp.setPassword(keystorePassword);
                    KeyManagersParameters kmp = new KeyManagersParameters();
                    kmp.setKeyStore(ksp);
                    kmp.setKeyPassword(keystorePassword);
                    ssl.setKeyManagers(kmp);
                }
                endpoint.setSslContextParameters(ssl);
            } catch (Exception e) {
                System.out.println("[WARN] SSLContextParameters (client) configuration failed: " + e.getMessage());
            }
        }

        return endpoint;
    }

    @Bean(name = "stateorprovinceretrievalcrp11x1partnerClient")
    public CxfEndpoint stateOrProvincePartnerClient(CamelContext camelContext) {
        CxfEndpoint endpoint = new CxfEndpoint();
        endpoint.setCamelContext(camelContext);
        endpoint.setAddress(clientsLoc3x1bAddress);
        endpoint.setDataFormat(org.apache.camel.component.cxf.common.DataFormat.PAYLOAD);
        endpoint.setServiceClass(Object.class);
        Map<String, Object> props = new HashMap<>();
        props.put("receiveTimeout", clientReceiveTimeoutMs);
        props.put("connectionTimeout", clientConnectTimeoutMs);
        endpoint.setProperties(props);
        return endpoint;
    }

    @Bean(name = "countryretrievalcrp10x1partnerClient")
    public CxfEndpoint countryPartnerClient(CamelContext camelContext) {
        CxfEndpoint endpoint = new CxfEndpoint();
        endpoint.setCamelContext(camelContext);
        endpoint.setAddress(clientsLoc3x1bAddress);
        endpoint.setDataFormat(org.apache.camel.component.cxf.common.DataFormat.PAYLOAD);
        endpoint.setServiceClass(Object.class);
        Map<String, Object> props = new HashMap<>();
        props.put("receiveTimeout", clientReceiveTimeoutMs);
        props.put("connectionTimeout", clientConnectTimeoutMs);
        endpoint.setProperties(props);
        return endpoint;
    }

    @Bean(name = "locationretrievalloc3x1mpartnerClient")
    public CxfEndpoint loc3x1mPartnerClient(CamelContext camelContext) {
        CxfEndpoint endpoint = new CxfEndpoint();
        endpoint.setCamelContext(camelContext);
        endpoint.setAddress(clientsLoc3x1bAddress);
        endpoint.setDataFormat(org.apache.camel.component.cxf.common.DataFormat.PAYLOAD);
        endpoint.setServiceClass(Object.class);
        Map<String, Object> props = new HashMap<>();
        props.put("receiveTimeout", clientReceiveTimeoutMs);
        props.put("connectionTimeout", clientConnectTimeoutMs);
        endpoint.setProperties(props);
        return endpoint;
    }
}