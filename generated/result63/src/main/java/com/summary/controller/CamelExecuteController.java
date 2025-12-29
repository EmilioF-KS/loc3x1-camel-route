
package com.summary.controller;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.support.DefaultExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CamelExecuteController {
    private static final Logger log = LoggerFactory.getLogger(CamelExecuteController.class);
    private final CamelContext camelContext;
    private final ProducerTemplate template;

    public CamelExecuteController(CamelContext camelContext, ProducerTemplate template) {
        this.camelContext = camelContext;
        this.template = template;
    }

    @PostMapping(path = "/api/camel/execute", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> execute(
            @RequestParam(name = "routeId", required = false) String routeId,
            @RequestBody(required = false) String body
    ) {
        String rid = (routeId == null || routeId.isBlank()) ? "mediation/identity" : routeId;
        String uri = "direct:" + rid;
        try {
            if (camelContext.hasEndpoint(uri) == null) {
                log.warn("Endpoint not found: {}", uri);
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_XML).body("<error>route not found</error>");
            }
            String payload = (body == null || body.isBlank()) ? "<root/>" : body;
            String out = template.requestBody(uri, payload, String.class);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(out != null ? out : "<ok/>");
        } catch (Exception e) {
            log.error("Camel execute failed for {}: {}", uri, e.getMessage(), e);
            return ResponseEntity.status(500).contentType(MediaType.APPLICATION_XML).body("<error>internal</error>");
        }
    }
}
