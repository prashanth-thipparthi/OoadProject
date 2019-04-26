/**
 * @author Amith Gopal/Prashanth Thipparthi
 */
package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.CustomJobAndApplicationInfo;
import com.example.demo.dao.*;
import com.example.demo.model.Application;
import com.example.demo.model.Job;

/*
 * This Company Controller contains handling REST APIs focused on
 * dealing with queries on the Company table in the database.
 */


@RestController
public class CompanyController {
	
	@Autowired
	LogInDAO dao;
	
	@Autowired
	JobDAO jdao;
	
	@Autowired
	ApplicationDAO adao;

	@Autowired
	CompanyDAO cdao;
	
	@Autowired
	CandidateDAO candao;
	
	@Autowired
	SampleDAO samdao;
	
	
	@PersistenceContext
	public EntityManager em;
	
	/*
	 * A Get request to get the complete list of all the jobs and the applications for each of those jobs for a requested company.
	 * Expects a parameter "id" which is the company id.
	 * Returns a custom created JSON containing list of all the job and it's application information for a requested company.
	 */
	
	@GetMapping(path="/getJobsForCompany")
	public List<CustomJobAndApplicationInfo> getJobsForCompany(@RequestParam("id") int id)
	{
		List<Job> jobs = jdao.findByCompanyObj(cdao.getOne(id));
		
		List<CustomJobAndApplicationInfo> cus = new ArrayList<CustomJobAndApplicationInfo>();
		
		jobs.forEach((job) -> {
			List<Application> apps = adao.findByJObjIn(job);
			cus.add(new CustomJobAndApplicationInfo(apps, job.getJobId(), job.getJobDescription(),
					job.getJobSkills(), job.getJobLocation(), job.getJobRole()));
		});
		return cus;
	}
	
	
}
;