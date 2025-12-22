package com.example.config;

import org.apache.camel.component.http.HttpClientConfigurer;
import org.apache.camel.component.http.HttpComponent;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {
    @Bean
    public HttpClientConfigurer httpClientConfigurer() {
        return clientBuilder -> {
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
            cm.setMaxTotal(100);
            cm.setDefaultMaxPerRoute(20);
            clientBuilder.setConnectionManager(cm);
            RequestConfig cfg = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(2000))
                .setResponseTimeout(Timeout.ofMilliseconds(5000))
                .build();
            clientBuilder.setDefaultRequestConfig(cfg);
        };
    }

    public HttpComponent customize(HttpComponent httpComponent, HttpClientConfigurer configurer) {
        httpComponent.setHttpClientConfigurer(configurer);
        return httpComponent;
    }
}
