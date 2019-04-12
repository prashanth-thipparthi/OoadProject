package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.*;
import com.example.demo.model.*;

@RestController
public class LogInController {
	
	@Autowired
	LogInDAO dao;
	
	@Autowired
	JobDAO jdao;

	@RequestMapping("users")
	public List<Login> getUsers() {
		
		List<Login> users = (List<Login>)dao.findAll(); 
				//new ArrayList<User>();
		
//		User u1 = new User();
//		u1.setId(100);
//		u1.setUsername("abc");
//		u1.setPassword("123");
//		
//
//		User u2 = new User();
//		u2.setId(101);
//		u2.setUsername("xyz");	
//		u2.setPassword("999");
//		
//		users.add(u1);
//		users.add(u2);
		
		return users;
		
	}
	
	@RequestMapping("jobs")
	public List<Job> getJobs() {
		
		List<Job> jobs = (List<Job>)jdao.findAll();
		return jobs;
	}
	
//	@PostMapping(path="/application/")
//	public Application addApplication()

	
}
