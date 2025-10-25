package com.ei.inbound;

import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.cxf.transport.servlet.CXFServlet;

import java.util.Collections;

@Configuration
public class CxfConfig {

    @Bean
    public ServletRegistrationBean<CXFServlet> cxfServlet() {
        ServletRegistrationBean<CXFServlet> servlet = new ServletRegistrationBean<>(new CXFServlet(), "/services/*");
        servlet.setName("CXFServlet");
        servlet.setLoadOnStartup(1);
        servlet.setInitParameters(Collections.singletonMap("bus", Bus.DEFAULT_BUS_ID));
        return servlet;
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus cxf() {
        return new SpringBus();
    }

    @Bean
    public Endpoint basicEndpoint(Bus bus) {
        BasicService impl = new BasicService();
        EndpointImpl endpoint = new EndpointImpl(bus, impl);
        endpoint.publish("/BasicService");
        return endpoint;
    }
}