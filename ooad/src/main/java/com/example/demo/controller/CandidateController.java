/**
 * @author Amith Gopal/Prashanth Thipparthi
 */
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

/*
 * This Candidate Controller contains handling REST APIs focused on
 * dealing with queries on the Candidate table in the database.
 */


@RestController
public class CandidateController {
	
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
	 * A Get Request to get the list of all the candidates with a certain set of given skills. Expected parameter is a string with comma separated values of different
	 * skills. 
	 * Returns List of Candidates possessing at least one of the skills provided.
	 */
	@GetMapping("candidates")
	public List<Candidate> getCandidates(@RequestParam("skills") String skills)
	{
		List<String> requestedSkills = Arrays.asList(skills.split(","));
		List<Candidate> filterBySkills = new ArrayList<Candidate>();
		List<Candidate> candidates = candao.findAll();
		System.out.println("Reached here");
		
		for(Candidate can: candidates)
		{
			System.out.println("CAN SKILLS " + can.getCandidateName());
			if (can.getSkills() == null)
				continue;
			
			List<String> canSkills = Arrays.asList(can.getSkills().toLowerCase().split(","));
			System.out.println(can.getSkills().toLowerCase());
			
			boolean flag = false;
			
			for (String rs : requestedSkills)
			{
				if (canSkills.contains(rs.toLowerCase()) == true)
				{
					flag = true;
					break;
				}
			}
			
			if (flag == true)
				filterBySkills.add(can);
		}		
		return filterBySkills;
	}

}