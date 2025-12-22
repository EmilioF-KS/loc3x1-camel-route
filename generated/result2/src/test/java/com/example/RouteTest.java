package com.example;

import org.apache.camel.CamelContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RouteTest {
    @Autowired
    CamelContext camelContext;

    @Test
    void contextLoads() {
        assertNotNull(camelContext);
    }
}

