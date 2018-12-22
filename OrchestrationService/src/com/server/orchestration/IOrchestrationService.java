package com.server.orchestration;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.db.biztalk.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.ws.Endpoint;

@WebService(name = "OrchestrationService", serviceName = "OrchestrationService", targetNamespace = "http://localhost:9000/HelloWorld", wsdlLocation = "http://localhost:9000/HelloWorld")
public interface IOrchestrationService {

    @WebMethod(action = "add_orchestration", operationName = "addOrchestration")
    boolean addOrchestration(@WebParam(name = "value") @XmlElement(required = true) Orchestration value);

    @WebMethod(action = "add_job", operationName = "addJob")
    boolean addJob(@WebParam(name = "value") @XmlElement(required = true) Job value);

    @WebMethod(action = "add_rule", operationName = "addRule")
    boolean addRule(@WebParam(name = "value") @XmlElement(required = true) Rule value);
}
