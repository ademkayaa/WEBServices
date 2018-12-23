package com.server.orchestration;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.db.biztalk.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@WebService(name = "OrchestrationService", targetNamespace = "http://orchestration.server.com")
public interface IOrchestrationService {

    /**
     * Introduce an orchestration.
     * @param value Object that contains orchestration information.
     * @param jobs List that contains jobs of orchestration.
     * @param rules List that contains rules of orchestration.
     * @return If added true, otherwise false.
     */
    @WebMethod(action = "add_orchestration", operationName = "addOrchestration")
    boolean addOrchestration(@WebParam(name = "orchVal")  @XmlElement(required = true) Orchestration value,
                             @WebParam(name = "jobList")  @XmlElement(required = true) List<Job> jobs,
                             @WebParam(name = "ruleList") @XmlElement(required = true) List<Rule> rules);

    /**
     * Add given job to database.
     * @param value Job to be added to database.
     * @return If added true, otherwise false.
     */
    @WebMethod(action = "add_job", operationName = "addJob")
    boolean addJob(@WebParam(name = "job") @XmlElement(required = true) Job value);

    /**
     * Add given rule to database.
     * @param value Rule to be added to database.
     * @return If added true, otherwise false.
     */
    @WebMethod(action = "add_rule", operationName = "addRule")
    boolean addRule(@WebParam(name = "rule") @XmlElement(required = true) Rule value);

}
