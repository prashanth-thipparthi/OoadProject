package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement
@Entity(name = "jobs")
@Table(name = "jobs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Job {
	
	@Id
	@Column(name = "job_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long jobId;
	private String jobskills;
	private String joblocation;
	private String jobdescription;
	private String job_role;
	
	@ManyToOne
	@JoinColumn(name="company_id")
	private Company companyObj;
	
	/*Getter and Setter for JobID*/ 
	public long getJobId()
	{
		return jobId;
	}
	
	public void setJobId(long jobId)
	{
		this.jobId = jobId;
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
	
	public Company getCompanyObj() {
		return companyObj;
	}

	public void setCompanyObj(Company companyObj) {
		this.companyObj = companyObj;
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
