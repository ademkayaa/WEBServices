package com.server.orchestration;

import com.biztalk.dao.*;

import javax.jws.WebService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebService(endpointInterface = "com.server.orchestration.IOrchestrationService",
            serviceName = "OrchestrationService")
public class OrchestrationService implements IOrchestrationService {

    /**
     * For accessing Job table
     */
    private JobDAO jobDAO = new JobDAO();

    /**
     * For accessing Rule table
     */
    private RuleDAO ruleDAO = new RuleDAO();

    /**
     * For accessing Orchestration table
     */
    private OrchestrationDAO orchestrationDAO = new OrchestrationDAO();

    /**
     * Introduce an orchestration.
     * @param value Object that contains orchestration information.
     * @param jobs List that contains jobs of orchestration.
     * @param rules List that contains rules of orchestration.
     * @return Message.
     */
    @Override
    public String addOrchestration(Orchestration value, List<Job> jobs, List<Rule> rules) {
        List<Integer> JobIdList = new ArrayList<>();
        List<Integer> RuleIdList = new ArrayList<>();

    	//End nodes
        JobIdList.add(0);
        RuleIdList.add(0);
    	
    	//Saving jobs to the database
    	// and adding their generated id's from db to JobIdList.
        for(Job temp : jobs)
            JobIdList.add(addJob(temp));

    	//Saving rules to db.
    	// creating edges between rules declared from gui.
    	// adding Rule id's to the RuleIdList.
        for(Rule temp : rules)
        {
            temp.setYesEdge(JobIdList.get(temp.getYesEdge()));
            temp.setNoEdge(JobIdList.get(temp.getNoEdge()));
            RuleIdList.add(this.addRule(temp));
        }
    	
    	//Updating all jobs with their associated rules with db ids.
        for(int search = 1; search < JobIdList.size(); ++search)
    	{
    	    Job workOn;
			try {
                workOn = jobDAO.getJob(search);
			} catch (Exception e1) {
                System.err.println("Error to access given id: " + search);
                return String.format("*** Error to access given id: %d***", search);
			}

            try {
                jobDAO.UpdateJob(search, "RULE_ID", RuleIdList.get(workOn.getRuleId() - 1));
            } catch (Exception e) {
                System.err.println("Error to access given id: " + RuleIdList.get(workOn.getRuleId() - 1));
                return String.format("*** Error to access given id: %d***", RuleIdList.get(workOn.getRuleId() - 1));
			}
    	}

    	// Set the start job's id.
    	value.setStartJobID(JobIdList.get(1));

    	// Adding orchestration value to db.
        try {
            orchestrationDAO.insertOrchestration(value);
        } catch (Exception e) {
    	    System.err.println("Orchestration could not be introduced: " + e);
            return "*** Orchestration could not be introduced. ***";
    	}
        return "Orchestration has been introduced successfully!";
    }

    /**
     * Add given job to database.
     * @param value Job to be added to database.
     * @return If added, returns job id which is got from db, otherwise -1 to indicate an error.
     */
    // Bu method eklenen job ın id sini döndürmeli ancak dbden öyle bi bilgi alamıyorum.
    private int addJob(Job value){
        int dbJobId;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String date = dateFormat.format(new Date());
            value.setInsertDateTime(date);
            value.setUpdateDateTime(date);
            value.setStatus(0);

            //JobID dbHandler tarafından doldurulmalı
            //value.setId(0);

            //RuleID GUI den doğru geldiğini düşünüyorum. Aksi takdirde db tarafından üretilen RuleID yi bi şekilde alabilmeliyim.
            //value.setRuleId(0);
        } catch(ParseException e){
            System.out.println("Date Parse error" + e);

            return -1;
        }

        try {
            dbJobId = jobDAO.insertJob(value);
        } catch(Exception e){
            System.out.println("Job Db insert error: " + e);
            return -1;
        }
        return dbJobId;
    }

    /**
     * Add given rule to database.
     * @param value Rule to be added to database.
     * @return If added, returns rule id which is got from db, otherwise -1 to indicate an error.
     */
    private int addRule(Rule value){
        int dbRuleId;

        try {
            dbRuleId = ruleDAO.insertRule(value);
        } catch(Exception e){
            System.out.println("Rule Db insert error: " + e);
            return -1;
        }
        return dbRuleId;
    }

}
