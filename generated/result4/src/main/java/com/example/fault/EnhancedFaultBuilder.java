package com.example.fault;

import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class EnhancedFaultBuilder {
    public String build(String code, String message, String details) {
        String ts = OffsetDateTime.now().toString();
        String cid = UUID.randomUUID().toString();
        return "<Fault>" +
                tag("timestamp", ts) +
                tag("correlationId", cid) +
                tag("code", safe(code)) +
                tag("message", safe(message)) +
                tag("details", safe(details)) +
                "</Fault>";
    }

    private String tag(String name, String value) {
        return "<" + name + ">" + value + "</" + name + ">";
    }

    private String safe(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}

