package com.example.demo.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@Autowired
	SampleDAO samdao;
	
	
	@PersistenceContext
	public EntityManager em;
	
	
	@GetMapping("/users")
	public List<Login> getUsers() {
		
		List<Login> users = (List<Login>)dao.findAll(); 
		return users;
	}
	
	@GetMapping("/candidates")
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
	
	@GetMapping("/jobs")
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
	
	
	
	@GetMapping(path="/deleteApplication")
	public boolean deleteApplication(@RequestParam("id") int id) 
	{
		if (adao.existsById(id) == true) {
			adao.deleteById(id);
			return true;
		}
		
		return false;
	}
	
	@GetMapping(path="/applicationStatus")
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
		j.addApplication(app);
		adao.save(app);
		jdao.save(j);
		return true;
	}

	@GetMapping(path="/getJobsForCompany")
	public List<Job> getJobsForCompany(@RequestParam("id") int id)
	{
		List<Job> jobs = jdao.findByCompanyObj(cdao.getOne(id));
		return jobs;
	}
	
	@GetMapping(path="/getRoles")
	public List<String> getRoles()
	{
		String query = "SELECT DISTINCT j.job_role FROM jobs j";
		List<String> roles = em.createQuery(query).getResultList();
		return roles;
	}
	
	@GetMapping(path="/getNotAppliedJobs")
	public List<Job> getNotAppliedJobs(@RequestParam("id") int canId)
	{
		List<Application> apps = adao.findJobObjByCandidateObj(candao.findById(canId).get());
		List<Long> appliedJobsID = new ArrayList<Long>();
		apps.forEach((app) -> appliedJobsID.add(app.getJob().getJobId()));
		
		if (appliedJobsID.isEmpty()) {
			// Return all jobs if no applications for the candidates are found.
			return jdao.findAll();
		}

		// Get jobs that the candidate has not applied to
		List<Job> jobs = jdao.findByJobIdNotIn(appliedJobsID);
		
		return jobs;
	}
	
	@GetMapping(path="/getAppliedJobs")
	public List<Application> getAppliedJobs(@RequestParam("id") int canId)
	{
		List<Application> apps = adao.findJobObjByCandidateObj(candao.findById(canId).get());
		return apps;
	}
	
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
	
	@PostMapping(path="/addJob")
	public boolean addJob(@RequestBody AddJobParameters addJobParams)
	{
		try {
			
			Job newJob = new Job();
			Company compObj = cdao.getOne(addJobParams.getCompany_id());
			newJob.setCompanyObj(compObj);
			newJob.setJobDescription(addJobParams.getDescription());
			newJob.setJobLocation(addJobParams.getLocation());
			newJob.setJobRole(addJobParams.getRole());
			newJob.setJobSkills(addJobParams.getSkills());
		
			jdao.save(newJob);
		}
		catch (Exception e)
		{
			System.out.println("Exception occurred");
			return false;
		}
		
		return true;
	}

	@GetMapping(path="/deleteJob")
	public boolean deleteJob(@RequestParam("id") long id)
	{
		if (jdao.existsById(id) == true)
		{
			jdao.deleteById(id);
			return true;
		}
		
		return false;
	}
	
	
	@PostMapping(path="/signup")//,consumes="{application/json}")
	public Login addUser(@RequestBody SignUpParameters signUp
						 ) 
	{				
		String qlString = "FROM login l WHERE l.username=" + signUp.getUsername();
		boolean notFound = true;
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
			else
			{
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
;