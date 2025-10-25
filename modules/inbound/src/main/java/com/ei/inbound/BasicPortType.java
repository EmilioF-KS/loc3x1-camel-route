package com.ei.inbound;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;

@WebService(targetNamespace = "http://ei.com/contracts/basic", name = "BasicPortType")
public interface BasicPortType {
    @WebMethod(operationName = "Ping")
    @WebResult(name = "reply")
    String ping(@WebParam(name = "message") String message);
}