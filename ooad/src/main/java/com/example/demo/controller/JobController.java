package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.*;
import com.example.demo.model.*;

@RestController
//@ComponentScan(basePackages = {"com.example.demo.model"}, basePackageClasses = Candidate.class)
public class JobController {
	
	@Autowired
	JobDAO jdao;
	
	@PersistenceContext
	public EntityManager em;

	@GetMapping("jobs")
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
}
;