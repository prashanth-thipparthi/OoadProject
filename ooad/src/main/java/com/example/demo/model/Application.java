package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "applications")
@IdClass(Application.class)
public class Application implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int application_id;
	
	@ManyToOne
	private Job job;
	
	@ManyToOne
	private Company comp;
	
	@ManyToOne
	private Candidate cand;
	
	@Id
	private int job_id;
	
	@Id
	private int candidate_id;
	
	@Id
	private int company_id;
	
	private Date creation_date;
	private String status;
	
	public int getApplicationId()
	{
		return application_id;
	}
	
	public void setApplicationId(int applicationId)
	{
		this.application_id = applicationId;
	}
	
	public int getJobId()
	{
		return job_id;
	}
	
	public void setJobId(int jobId)
	{
		this.job_id = jobId;
	}
	
	public int getCandidateId()
	{
		return candidate_id;
	}
	
	public void setCandidateId(int candidateId)
	{
		this.candidate_id = candidateId;
	}
	
	public int getCompanyId()
	{
		return company_id;
	}
	
	public void setCompanyId(int companyId)
	{
		this.company_id = companyId;
	}
	
	public Date getCreationDate()
	{
		return creation_date;
	}
	
	public void setCreationDate(Date date)
	{
		this.creation_date = date;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Company getComp() {
		return comp;
	}

	public void setComp(Company comp) {
		this.comp = comp;
	}

	public Candidate getCand() {
		return cand;
	}

	public void setCand(Candidate cand) {
		this.cand = cand;
	}
}
