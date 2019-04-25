package com.example.demo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
@Entity(name = "jobs")
@Table(name = "jobs")
@JsonAutoDetect
public class Job implements Serializable {
	
	@Id
	@Column(name = "job_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long jobId;
	
	@Column(name="jobskills")
	@JsonProperty("skills")
	private String jobskills;
	
	@Column(name="joblocation")
	@JsonProperty("location")
	private String joblocation;
	
	@Column(name="jobdescription")
	@JsonProperty("description")
	private String jobdescription;
	
	@Column(name="job_role")
	@JsonProperty("role")
	private String job_role;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="jObj")
	private List<Application> appIdList;
	
	@ManyToOne
	@JoinColumn(name="company_id")
	private Company companyObj;
	
	public Job() {
		this.appIdList  = new ArrayList<Application>();
	}
	
	@JsonIgnore
	public List<Application> getApplicationList()
	{
		return this.appIdList;
	}

	public void addApplication(Application app) 
	{
		this.appIdList.add(app);
	}
	
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
