package com.server.orchestration;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.db.biztalk.*;

import javax.xml.ws.Endpoint;

@WebService(name = "OrchestrationService", targetNamespace = "")
public interface IOrchestrationService {

    @WebMethod(action = "add_orchestration", operationName = "addOrchestration")
    boolean addOrchestration(@WebParam(name = "value") Orchestration value);

    @WebMethod(action = "add_job", operationName = "addJob")
    boolean addJob(@WebParam(name = "value") Job value);

    @WebMethod(action = "add_rule", operationName = "addRule")
    boolean addRule(@WebParam(name = "value") Rule value);
}
