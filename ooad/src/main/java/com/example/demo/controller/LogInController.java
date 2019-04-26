/**
 * @author Amith Gopal
 */
package com.example.demo.controller;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.SignUpParameters;
import com.example.demo.dao.*;
import com.example.demo.model.*;

/*
 * This Login Controller contains handling REST APIs focused on
 * dealing with queries on the Login, Candidate and Company tables in the database.
 */


@RestController
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
	
	/*
	 * A Get Request for signing in to the Job Portal. Expects request parameters like username and password.
	 * Returns either the Candidate or a Company object based on the username.
	 */
	
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
	
	/*
	 *  A Post request used to sign up either a customer or a candidate into the Job Portal. Expects parameters like username, password, flag 
	 *  representing whether it is a candidate or a company and name.
	 *  Returns the Login object representing the user.
	 */
	
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
	
	/*
	 * A Delete request in order to delete a user. Expects a parameter "username".
	 * Returns a String indicating the status of the delete operation.
	 */
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