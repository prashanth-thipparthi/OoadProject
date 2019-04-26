/**
 * @author Amith Gopal
 */
package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.AddJobParameters;
import com.example.demo.dao.*;
import com.example.demo.model.*;

/*
 * This Job Controller contains handling REST APIs focused on
 * dealing with queries on the Job table in the database.
 */


@RestController
public class JobController {
	
	@Autowired
	JobDAO jdao;
	
	@Autowired
	ApplicationDAO adao;
	
	@Autowired
	CandidateDAO candao;
	
	@Autowired
	CompanyDAO cdao;
	
	@Autowired
	SkillsDAO skilldao;
	
	@PersistenceContext
	public EntityManager em;

	
	/*
	 * A Post request to add the skills in the Skills table. Expected parameter is a string consisting of comma separated values representing the skills.
	 * Returns true if the skills are successfully added to the skills table otherwise it returns false.
	 */
	@PostMapping(path="/skills")
	public boolean addSkills(@RequestParam String skills )
	{
		try {
		
			String lowercaseSkills = skills.toLowerCase();
			String skill[] = lowercaseSkills.split(",");
			for(String sk : skill) {
				Skills s = new Skills(sk);
				skilldao.save(s);
			}
		}
		catch (Exception e)
		{
			System.out.println("Exception occurred");
			return false;
		}
		
		return true;
	}
	
	/*
	 * A Get request to get the list of all the unique skills from the skills table.
	 * No parameters expected.
	 * Returns a list of all the unique skills from the skills table
	 */
	
	@GetMapping("/skills")
	public List<Skills> getSkills() {
		
		return skilldao.findAll();
	}
	
/*
 * A Get Request to get the list of all the jobs which requires at least one of the requested skills and has openings for at least one of the requested roles.
 * Expects 2 parameters: one being a string representing a comma separated value of skills and one being role representing a comma separated value of roles.
 * Returns a List of all the jobs requiring at least one of the requested skills and at least one of the requested roles.
 */
	@GetMapping("/jobs")
	public List<Job> getJobs(@RequestParam("skills") String skills, 
							 @RequestParam("role") String role) {
		
		
		List<Job> queriedJobs = null;
		//System.out.println("SSSSSSSSSSSSKILLLLLLLLLLLLLLLLLLLLLLSSSSSSSSSSSSS" + skills);
		List<String> requestedRoles = Arrays.asList(role.split(","));
		System.out.println(requestedRoles.size());
		if (requestedRoles.size() == 1 && requestedRoles.get(0).equals(""))
		{	
			System.out.println("IT IS EMPTY");
			queriedJobs = jdao.findAll();
			
		}
		else
		{
			//queriedJobs = jdao.findByJobrole(role);
			String rolesQuery = " ( ";
			int i;
			String mainQuery = "FROM jobs j WHERE ";
			for (i=0; i<requestedRoles.size()-1;i++) {
				rolesQuery += " j.job_role='" + requestedRoles.get(i).toUpperCase() + "' OR ";
			}
			rolesQuery += " j.job_role='" + requestedRoles.get(i) +"')";
				
			mainQuery += rolesQuery;
			System.out.println(mainQuery);
			queriedJobs = (List<Job>) em.createQuery(mainQuery).getResultList();
		}
		
		//return queriedJobs;
		
		List<String> requestedSkills = Arrays.asList(skills.split(","));
		
		System.out.println(queriedJobs);
		if (requestedSkills.size() == 1 && requestedSkills.get(0).equals("")) {
			return queriedJobs;
		}
		
		List<Job> filterBySkills = new ArrayList<Job>();
		
		for(Job job:queriedJobs) {
			List<String> jobSkills = Arrays.asList(job.getJobSkills().toLowerCase().split(","));
			
//			for(String js: jobSkills)
//				System.out.println("JOB SKILLS" + js);
//			
//			for(String rs: requestedSkills)
//				System.out.println("REQUESTED SKILLS" + rs);
			
			
			boolean flag = false;

			for(String rs:requestedSkills)
			{
				if (jobSkills.contains(rs.toLowerCase()) == true)
				{
					flag = true;
					break;
				}
			}
			
			if (flag == true) {
				System.out.println("Added");
				filterBySkills.add(job);
			}		
		}
		return filterBySkills;
	}
	
	
	/*
	 * A Get request to get the list of all the jobs which a candidate with id "id" hasn't applied to yet. 
	 * Expects a parameter id representing the candidate id.
	 * Returns a list of all the jobs which a candidate hasn't applied to yet.
	 */
	
	@GetMapping(path="/getNotAppliedJobs")
	public List<Job> getNotAppliedJobs(@RequestParam("id") int canId)
	{
		List<Application> apps = adao.findJobObjByCandidateObj(candao.findById(canId).get());
		List<Long> appliedJobsID = new ArrayList<Long>();
		apps.forEach((app) -> appliedJobsID.add(app.getJob().getJobId()));
		
		if (appliedJobsID.isEmpty()) {
			// Return all jobs if no applications for the candidates are found.
			return jdao.findAll();
		}

		// Get jobs that the candidate has not applied to
		List<Job> jobs = jdao.findByJobIdNotIn(appliedJobsID);
		
		return jobs;
	}
	
	/*
	 * A Post request made by a Company User to add a job to the Job portal database. 
	 * Expects the parameters like skills, location, description, role, company_id.
	 * Returns true if the job has been saved successfully otherwise returns false.
	 */
	
	@PostMapping(path="/addJob")
	public boolean addJob(@RequestBody AddJobParameters addJobParams)
	{
		JobController jc = new JobController();
		
		try {
			
			Job newJob = new Job();
			Company compObj = cdao.getOne(addJobParams.getCompany_id());
			newJob.setCompanyObj(compObj);
			newJob.setJobDescription(addJobParams.getDescription());
			newJob.setJobLocation(addJobParams.getLocation());
			newJob.setJobRole(addJobParams.getRole());
			newJob.setJobSkills(addJobParams.getSkills());
			if (jc.addSkills(addJobParams.getSkills()) == true)
			{
				System.out.println("The skills have been added to the skills table successfully.");
			}
			else
			{ 
				System.out.println("ERRRORRR: The skills have not been added to the skills table");
			}
		
			jdao.save(newJob);
		}
		catch (Exception e)
		{
			System.out.println("Exception occurred");
			return false;
		}
		
		return true;
	}
	
	/*
	 * A Get Request to get a List of all the unique roles for all the jobs in the Jobs table.
	 * Expects no parameters.
	 * Returns a list of unique roles like ['SDE', 'SDE1', 'SDE2']
	 */
	@GetMapping(path="/getRoles")
	public List<String> getRoles()
	{
		String query = "SELECT DISTINCT j.job_role FROM jobs j";
		List<String> roles = em.createQuery(query).getResultList();
		return roles;
	}
	
	
	/*
	 * A Delete request to delete a job with a given id.  
	 * Expects a parameter "id" indicating a job id.
	 * Returns true if the deletion is successful else Returns false.
	 */
	
	@DeleteMapping(path="/deleteJob")
	public boolean deleteJob(@RequestParam("id") long id)
	{
		if (jdao.existsById(id) == true)
		{
			jdao.deleteById(id);
			return true;
		}
		
		return false;
	}
	
	
}
;