package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.*;
import com.example.demo.model.*;

@RestController
//@ComponentScan(basePackages = {"com.example.demo.model"}, basePackageClasses = Candidate.class)
public class ApplicationController {
	
	@Autowired
	JobDAO jdao;
	
	@Autowired
	ApplicationDAO adao;
	
	@Autowired
	CandidateDAO candao;
	
	@Autowired
	SampleDAO samdao;
	
	
	@PersistenceContext
	public EntityManager em;
	
	@PostMapping(path="/application")
	public Application addApplication(@RequestParam("application_id") String applicationId,
							   @RequestParam("job_id") String jobId,
							   @RequestParam("candidate_id") String candidateId,
							   @RequestParam("creation_date") String creationDate,
							   @RequestParam("status") String status) throws ParseException
	{
		
		int appId = Integer.parseInt(applicationId);
		long jId = Integer.parseInt(jobId); //done
		int canId = Integer.parseInt(candidateId); //done
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(creationDate);
		
		System.out.println(appId + "-----" + jId + "--------" + canId + "------------------------" + date + "------");
		
		Application app = new Application();
		
		//app.setApplicationId(appId);
		Candidate candi = candao.getOne(canId);
		Job j = jdao.getOne(jId);
		
		app.setCandidate(candi);
		app.setJob(j);
		
		app.setCreationDate(date);
		app.setStatus(status);
		
		adao.save(app);
		
		return app;
	}
}