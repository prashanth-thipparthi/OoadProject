package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
	
//	//@RequestMapping(path="users/{userid}",produces={"application/xml"})
//	@GetMapping("/users/{userid}")
//	public Optional<Login> getUsers(@PathVariable("userid") int userid) {
//			return dao.findById(userid);	
//	}
//	
	
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
	
	
	@GetMapping("jobs")
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

	@PostMapping(path="/sample")
	public Sample addSample(@RequestParam("sample_id") String sampleId,
							   @RequestParam("company_id") String companyId) throws ParseException
	{
		
		int samId = Integer.parseInt(sampleId);
		int comId = Integer.parseInt(companyId);
				
		Sample sam = new Sample();
		
		//sam.setSampleId(samId);
		Company comp = cdao.getOne(comId);
		sam.setCompanyObj(comp);
	
		
		samdao.save(sam);
		
		return sam;
	}

	@GetMapping(path="/getRoles")
	public List<String> getRoles()
	{
		String query = "SELECT DISTINCT j.job_role FROM jobs j";
		List<String> roles = em.createQuery(query).getResultList();
		return roles;
	}
	
	@GetMapping(path="/getNotAppliedJobs")
	public List<Application> getNotAppliedJobs(@RequestParam("id") int canId)
	{
		List<Application> apps = adao.findByCandidateObjNotIn(candao.findById(canId).get());
		return apps;
	}
	
	@GetMapping(path="/getAppliedJobs")
	public List<Application> getAppliedJobs(@RequestParam("id") int canId)
	{
		//List<Application> jobs = adao.findJobObjByCandidateObj(candao.findById(canId).get());
		//List<Application> jobs = adao.findByCandidateObjNotIn(candao.findById(canId).get());
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
	

	@PostMapping(path="/signup")//,consumes="{application/json}")
	public Login addUser(@RequestBody SignUpParameters signUp
						 ) {		
		
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
	
//	@PutMapping(path="/users")//,consumes="{application/json}")
//	public Login saveOrAddUser(@RequestBody Login user) {
//		dao.save(user);
//		return user;
//	}
	
	@DeleteMapping("/deleteUser")
	public String deleteUser(@RequestParam("username") String username) {
		//return dao.findById(userid);
//		Login user = dao.getOne(userid);
//		dao.delete(user);	
		
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