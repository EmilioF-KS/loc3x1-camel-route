package com.ei.inbound;

import jakarta.jws.WebService;

@WebService(
    serviceName = "BasicService",
    portName = "BasicPort",
    targetNamespace = "http://ei.com/contracts/basic",
    endpointInterface = "com.ei.inbound.BasicPortType"
)
public class BasicService implements BasicPortType {
    @Override
    public String ping(String message) {
        return "PONG:" + (message == null ? "" : message);
    }
}