package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "jobs")
public class Job {
	
	@Id
	private long job_id;
	private String jobskills;
	private String joblocation;
	private String jobdescription;
	private String job_role;
	
	/*Getter and Setter for JobID*/ 
	public long getJobId()
	{
		return job_id;
	}
	
	public void setJobId(long jobId)
	{
		this.job_id = jobId;
	}
	
	/*Getter and Setter for Job Skills*/
	public String getJobSkills()
	{
		return jobskills;
	}
	
	public void setJobSkills(String jobSkills)
	{
		this.jobskills = jobSkills;
	}
	
	/*Getter and Setter for Job Location*/
	public String getJobLocation()
	{
		return joblocation;
	}
	
	public void setJobLocation(String jobLocation)
	{
		this.joblocation = jobLocation;
	}
	
	/*Getter and Setter for Job Description*/
	public String getJobDescription()
	{
		return jobdescription;
	}
	
	public void setJobDescription(String jobDescription)
	{
		this.jobdescription = jobDescription;
	}
	
	/*Getter and Setter for Job Role*/
	public String getJobRole()
	{
		return this.job_role;
	}

	public void setJobRole(String jobRole)
	{
		this.job_role = jobRole;
	}
}