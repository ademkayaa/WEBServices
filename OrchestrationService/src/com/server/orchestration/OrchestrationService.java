package com.server.orchestration;

import javax.jws.WebService;

@WebService(endpointInterface = "com.server.orchestration.IOrchestrationService")
public class OrchestrationService implements IOrchestrationService {

    @Override
    public boolean addOrchestration(String from){
        return false;
    }

    @Override
    public boolean addJob(String from){
        return false;
    }

    @Override
    public boolean addRule(String from){
        return false;
    }
}
