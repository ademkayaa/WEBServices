package com.server.orchestration;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService(name = "OrchestrationService", targetNamespace = "")
public interface IOrchestrationService {

    @WebMethod(action = "add_orchestration", operationName = "addOrchestration")
    boolean addOrchestration(@WebParam(name = "from") String from);

    @WebMethod(action = "add_job", operationName = "addJob")
    boolean addJob(@WebParam(name = "from") String from);

    @WebMethod(action = "add_rule", operationName = "addRule")
    boolean addRule(@WebParam(name = "from") String from);

    public static void main(String[] argv) {
        Object implementor = new OrchestrationService();
        String address = "http://localhost:9000/HelloWorld";
        Endpoint.publish(address, implementor);
    }
}
