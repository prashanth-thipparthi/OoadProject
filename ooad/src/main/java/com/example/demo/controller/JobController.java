package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.AddJobParameters;
import com.example.demo.dao.*;
import com.example.demo.model.*;

@RestController
//@ComponentScan(basePackages = {"com.example.demo.model"}, basePackageClasses = Candidate.class)
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
	
	@GetMapping("/skills")
	public List<Skills> getSkills() {
		
		return skilldao.findAll();
	}
	
	@GetMapping("/jobs")
	public List<Job> getJobs(@RequestParam("skills") String skills, 
							 @RequestParam("role") String role) {
		
		
		List<String> requestedRoles = Arrays.asList(role.split(","));
		String rolesQuery = " ( ";
		int i;
		String mainQuery = "FROM jobs j WHERE ";
		for (i=0; i<requestedRoles.size()-1;i++) {
			rolesQuery += " j.job_role='" + requestedRoles.get(i).toUpperCase() + "' OR ";
		}
		rolesQuery += " j.job_role='" + requestedRoles.get(i) +"')";
				
		mainQuery += rolesQuery;
		
		List<Job> queriedJobs = (List<Job>) em.createQuery(mainQuery).getResultList();
		
		
		List<String> requestedSkills = Arrays.asList(skills.split(","));
		List<Job> filterBySkills = new ArrayList<Job>();
		
		for(Job job:queriedJobs) {
			List<String> jobSkills = Arrays.asList(job.getJobSkills().toLowerCase().split(","));
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
				filterBySkills.add(job);
			}		
		}
		return filterBySkills;
	}
	
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
	
	@PostMapping(path="/addJob")
	public boolean addJob(@RequestBody AddJobParameters addJobParams)
	{
		try {
			
			Job newJob = new Job();
			Company compObj = cdao.getOne(addJobParams.getCompany_id());
			newJob.setCompanyObj(compObj);
			newJob.setJobDescription(addJobParams.getDescription());
			newJob.setJobLocation(addJobParams.getLocation());
			newJob.setJobRole(addJobParams.getRole());
			newJob.setJobSkills(addJobParams.getSkills());
		
			jdao.save(newJob);
		}
		catch (Exception e)
		{
			System.out.println("Exception occurred");
			return false;
		}
		
		return true;
	}
	
	@GetMapping(path="/getRoles")
	public List<String> getRoles()
	{
		String query = "SELECT DISTINCT j.job_role FROM jobs j";
		List<String> roles = em.createQuery(query).getResultList();
		return roles;
	}
	
	

	@GetMapping(path="/deleteJob")
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