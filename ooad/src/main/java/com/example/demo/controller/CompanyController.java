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

@RestController
//@ComponentScan(basePackages = {"com.example.demo.model"}, basePackageClasses = Candidate.class)
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