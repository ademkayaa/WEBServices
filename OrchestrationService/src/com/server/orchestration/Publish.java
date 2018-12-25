package com.server.orchestration;

import javax.xml.ws.Endpoint;

public class Publish {

    public static void main(String[] argv) {
        Object implementor = new OrchestrationService();
        String address = "http://localhost:9001/OrchestrationService";
        Endpoint.publish(address, implementor);
    }

}
