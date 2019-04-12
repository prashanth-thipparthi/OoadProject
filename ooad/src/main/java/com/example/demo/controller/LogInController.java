package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.*;
import com.example.demo.model.*;

@RestController
public class LogInController {
	
	@Autowired
	LogInDAO dao;
	
	@Autowired
	JobDAO jdao;
	
	@Autowired
	ApplicationDAO adao;

	@GetMapping("/users")
	public List<Login> getUsers() {
		
		List<Login> users = (List<Login>)dao.findAll(); 
		
		/*
		User u1 = new User();
		u1.setId(100);
		u1.setUsername("abc");
		u1.setPassword("123");
		

		User u2 = new User();
		u2.setId(101);
		u2.setUsername("xyz");
		u2.setPassword("999");
		
		users.add(u1);
		users.add(u2); */
		
		return users;
	}
	
	//@RequestMapping(path="users/{userid}",produces={"application/xml"})
	@GetMapping("/users/{userid}")
	public Optional<Login> getUsers(@PathVariable("userid") int userid) {
			return dao.findById(userid);	
	}
	

	@GetMapping("jobs")
	public List<Job> getJobs() {
		
		List<Job> jobs = (List<Job>)jdao.findAll();
		return jobs;
	}
	
	@PostMapping(path="/application")
	public Application addApplication(@RequestParam("application_id") String applicationId,
							   @RequestParam("job_id") String jobId,
							   @RequestParam("candidate_id") String candidateId,
							   @RequestParam("company_id") String companyId,
							   @RequestParam("creation_date") String creationDate,
							   @RequestParam("status") String status) throws ParseException
	{
		
		int appId = Integer.parseInt(applicationId);
		int jId = Integer.parseInt(jobId);
		int canId = Integer.parseInt(candidateId);
		int comId = Integer.parseInt(companyId);
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(creationDate);
		
		System.out.println(appId + "-----" + jId + "--------" + canId + "-----------" + comId + "-------------" + date + "------");
		
		Application app = new Application();
		
		//app.setApplicationId(appId);
		app.setCandidateId(canId);
		app.setCompanyId(comId);
		app.setCreationDate(date);
		app.setJobId(jId);
		app.setStatus(status);
		
		adao.save(app);
		
		return app;
	}


	@PostMapping(path="/users")//,consumes="{application/json}")
	public Login addUser(@RequestBody Login user) {
		dao.save(user);
		return user;
	}
	
	@PutMapping(path="/users")//,consumes="{application/json}")
	public Login saveOrAddUser(@RequestBody Login user) {
		dao.save(user);
		return user;
	}
	
	@DeleteMapping("/users/{userid}")
	public String deleteUser(@PathVariable("userid") int userid) {
		//return dao.findById(userid);
		Login user = dao.getOne(userid);
		dao.delete(user);		
		return "deleted";
	}

	
}
