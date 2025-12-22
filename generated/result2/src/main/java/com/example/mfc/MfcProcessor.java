package com.example.mfc;

import org.springframework.stereotype.Component;

@Component
public class MfcProcessor {
    public String buildSimpleFault(String message) {
        String m = message == null ? "" : message;
        return "<SimpleFault><Message>" + escape(m) + "</Message></SimpleFault>";
    }

    private String escape(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
