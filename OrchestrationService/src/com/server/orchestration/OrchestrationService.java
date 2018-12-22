package com.server.orchestration;

import com.db.biztalk.*;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@WebService(endpointInterface = "com.server.orchestration.IOrchestrationService")
public class OrchestrationService implements IOrchestrationService {

    @Override
    public boolean addOrchestration(Orchestration value, List<Job> jobs, List<Rule> rules, String Username)
    {
    	List<Integer> JobIdList = new ArrayList<>();
    	List<Integer> RuleIdList = new ArrayList<>();

    	//End nodes
    	JobIdList.add(0);
    	RuleIdList.add(0);
    	
    	//Saving jobs to the database
    	// and adding their generated id's from db to JobIdList.
    	for(Job temp : jobs)
    		JobIdList.add(addJobSub(temp));

    	//Saving rules to db.
    	// creating edges between rules declared from gui.
    	// adding Rule id's to the RuleIdList.
    	for(Rule temp : rules)
    	{
    		temp.setYesEdge(JobIdList.get(temp.getYesEdge()));
    		temp.setNoEdge(JobIdList.get(temp.getNoEdge()));
    		RuleIdList.add(this.addRuleSub(temp));
    	}
    	
    	//Updating all jobs with their associated rules with db ids.
    	for(int search = 1; search < JobIdList.size(); ++search)
    	{
    		JobDAO accessingDb = new JobDAO();
    		Job workon;
			try {
				workon = accessingDb.getJob(search);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.err.println("Error to acces given id");
				return false;
			}
    		try {
				accessingDb.UpdateJob(search, "RuleId", RuleIdList.get(workon.getRuleId() - 1));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.err.println("Error to acces given id");
				return false;
			}
    	}
        return true;
    }

    
    @Override
    public boolean addJob(Job value){
        return addJobSub(value) > 0;
    }

    @Override
    public boolean addRule(Rule value){
        return addRuleSub(value) > 0;
    }

    // Bu method eklenen job ın id sini döndürmeli ancak dbden öyle bi bilgi alamıyorum.
    private int addJobSub(Job value){
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String date = dateFormat.format(new Date());
            value.setInsertDateTime(date);
            value.setUpdateDateTime(date);
            value.setStatus(0);

            //JobID dbHandler tarafından doldurulmalı
            //value.setId(0);

            //RuleID GUI den doğru geldiğini düşünüyorum. Aksi takdirde db tarafından üretilen RuleID yi bi şekilde alabilmeliyim.
            //value.setRuleId(0);
        }
        catch(ParseException e){
            System.out.println("Date Parse error" + e);

            return -1;
        }

        JobDAO jobDB = new JobDAO();
        try {
            jobDB.insertJob(value);
        }
        catch(Exception e){
            System.out.println("Job Db insert error: " + e);

            return -1;
        }

        return 1;
    }

    private int addRuleSub(Rule value){
        RuleDAO ruleDB = new RuleDAO();
        try {
            ruleDB.insertRule(value);
        }
        catch(Exception e){
            System.out.println("Rule Db insert error: " + e);
            return -1;
        }

        return 1;
    }

    public static void main(String[] argv) {
        Object implementor = new OrchestrationService();
        String address = "http://localhost:9000/HelloWorld";
        Endpoint.publish(address, implementor);
    }
}
