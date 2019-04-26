/**
 * @author Amith Gopal/Prashanth Thipparthi
 */
package com.example.demo.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.AddApplicationParameters;
import com.example.demo.dao.*;
import com.example.demo.model.*;

/*
 * This Application Controller contains handling REST APIs focused on
 * dealing with queries on the Application table in the database.
 */

@RestController
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
	
	/*
	 * Get Request to delete an application. Expects a request parameter of "id"
	 * Return type is true if delete is successful otherwise false
	 */
	
	@GetMapping(path="/deleteApplication")
	public boolean deleteApplication(@RequestParam("id") int id) 
	{
		if (adao.existsById(id) == true) {
			adao.deleteById(id);
			return true;
		}
		
		return false;
	}
	
	
	/*
	 * Put Request to change the status of an application. Expects 2 request parameters, one status and the other application id.
	 * Returns true if the status change is successful else returns false.
	 */
	
	@PutMapping(path="/applicationStatus")
	public boolean changeApplicationStatus(@RequestParam("status") String status,
											@RequestParam("id") int id)
	{
		try {
			Application app = adao.findById(id).get();
			app.setStatus(status);
			adao.save(app);
		}
		catch(Exception e) 
		{
			System.out.println("Exception");
			System.out.println(e);
			return false;
		}
		
		return true;
	}
	
	
	/*
	 * Post Request to add an application in the application database. Expects parameters like job_id, candidate_id, status.
	 * Returns true if the application is saved successfully in the database otherwise returns false.
	 */
	@PostMapping(path="/application")
	public boolean addApplication(@RequestBody AddApplicationParameters addApplicationParams) throws ParseException
	{
		
		long jId = Integer.parseInt(addApplicationParams.getJob_id());
		int canId = Integer.parseInt(addApplicationParams.getCandidate_id());
		
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String strDate = dateFormat.format(date);
		Date date1 = dateFormat.parse(strDate);
				
		Application app = new Application();
		
		Candidate candi = candao.getOne(canId);
		Job j = null;
		if (jdao.existsById(jId) == true)
		{
			j = jdao.findById(jId).get();
			
		}
		else
		{
			return false;
		}
		app.setCandidate(candi); 
		app.setJob(j);
		app.setCreationDate(date1);
		app.setStatus(addApplicationParams.getStatus());
		adao.save(app);
		return true;
	}
	
	
	/*
	 * Get all the applications of a candidate with id "id". Expects a parameter "id".
	 * Returns list of the applications of the candidate.
	 */
	
	@GetMapping(path="/getAppliedJobs")
	public List<Application> getAppliedJobs(@RequestParam("id") int canId)
	{
		List<Application> apps = adao.findJobObjByCandidateObj(candao.findById(canId).get());
		return apps;
	}
	
	
}