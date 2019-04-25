package com.example.demo.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

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

import com.example.demo.AddApplicationParameters;
import com.example.demo.AddJobParameters;
import com.example.demo.SignUpParameters;
import com.example.demo.dao.*;
import com.example.demo.model.*;

@RestController
//@ComponentScan(basePackages = {"com.example.demo.model"}, basePackageClasses = Candidate.class)
public class LogInController {
	
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
	
	@PersistenceContext
	public EntityManager em;	
	
	@GetMapping(path="/signin")
	public Object getUser(@RequestParam("username") String username, 
									  @RequestParam("password") String password) {
		
		Object ret = null;
		
		if (dao.existsById(username) == true)
		{
			Login requestedUser = dao.findById(username).get();
			if (requestedUser.getPassword().equals(password))
			{
				if (requestedUser.isFlag() == true)
				{
					return candao.findByUser(requestedUser);

				}
				else
				{
					return cdao.findByUser(requestedUser);
					
				}
			}
		}
		
		return ret;
	}
	
	@PostMapping(path="/signup")
	public Login addUser(@RequestBody SignUpParameters signUp) {
		
		Login user = null;
			
		if (dao.existsById(signUp.getUsername()) == false)
		{
			user = new Login();
			user.setFlag(signUp.isFlag());
			user.setPassword(signUp.getPassword());
			user.setUsername(signUp.getUsername());
			dao.save(user);
			
			System.out.println("FLAG is " + user.isFlag());
			
			if (signUp.isFlag())
			{
				System.out.println("Candidate section");
				Candidate can = new Candidate();
				can.setCandidateName(signUp.getName());
				can.setUser(user);
				candao.save(can);
				
			}
			else  {
				System.out.println("Company section");
				Company com = new Company();
				com.setCompanyname(signUp.getName());
				com.setUser(user);
				cdao.save(com);
			}
		}
		else {
			System.out.println("This user already exists");
		}
		return user;
	}
	
	@DeleteMapping("/deleteUser")
	public String deleteUser(@RequestParam("username") String username) 
	{

		String deleteMessage = "Cannot delete as the user " + username + " doesn't exist"; 
		if (dao.existsById(username) == true) {
			System.out.println("The user " + username + " exists. Going to delete ....");
			Login user = dao.getOne(username);
			dao.delete(user);
			deleteMessage = "The user " + username + " deleted.";
		}
		return deleteMessage;
	}

	
}