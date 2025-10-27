package com.ei.camel.orchestration.testconfig;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 * Minimal Spring Boot configuration for tests to enable auto-configuration
 * without component scanning. This ensures Camel auto-config provides
 * a CamelContext bean while avoiding loading unrelated routes.
 */
@SpringBootConfiguration
@EnableAutoConfiguration
public class TestAutoConfig {
}