package com.ei.clients;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.dynamic.DynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

public class BasicServiceSoapClientRunner {
    public static void main(String[] args) throws Exception {
        String port = System.getenv().getOrDefault("INBOUND_PORT", "8085");
        String defaultWsdl = String.format("http://localhost:%s/services/BasicService?wsdl", port);
        String wsdl = System.getenv().getOrDefault("BASIC_SERVICE_WSDL", defaultWsdl);
        String msg = System.getenv().getOrDefault("PING_MSG", "client");
        long connectTimeout = Long.parseLong(System.getenv().getOrDefault("CLIENT_CONNECT_TIMEOUT_MS", "5000"));
        long receiveTimeout = Long.parseLong(System.getenv().getOrDefault("CLIENT_RECEIVE_TIMEOUT_MS", "5000"));

        System.out.println("CXF DynamicClient invoking Ping: wsdl=" + wsdl + ", msg=" + msg);
        DynamicClientFactory dcf = DynamicClientFactory.newInstance();
        Client client = dcf.createClient(wsdl);

        // Configure timeouts on HTTP conduit
        HTTPConduit conduit = (HTTPConduit) client.getConduit();
        HTTPClientPolicy policy = new HTTPClientPolicy();
        policy.setConnectionTimeout(connectTimeout);
        policy.setReceiveTimeout(receiveTimeout);
        conduit.setClient(policy);

        Object[] response = client.invoke("Ping", msg);
        String reply = (String) response[0];
        System.out.println("Ping reply=" + reply);
        String expected = "PONG:" + msg;
        if (!expected.equals(reply)) {
            System.err.println("Unexpected reply; expected=" + expected);
            System.exit(2);
        }
        System.out.println("✓ CXF client Ping succeeded");
    }
}