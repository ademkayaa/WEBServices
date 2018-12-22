package com.server.orchestration;

import com.db.biztalk.Job;
import com.db.biztalk.Orchestration;
import com.db.biztalk.Rule;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService(endpointInterface = "com.server.orchestration.IOrchestrationService")
public class OrchestrationService implements IOrchestrationService {

    @Override
    public boolean addOrchestration(Orchestration value){
        return false;
    }

    @Override
    public boolean addJob(Job value){
        return false;
    }

    @Override
    public boolean addRule(Rule value){
        return false;
    }
}
