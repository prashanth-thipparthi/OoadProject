package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "applications") 
	   //uniqueConstraints= @UniqueConstraint(columnNames={"job_id", "candidate_id"}))
public class Application  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int application_id;	
	
//	@Id
//	@Column(name="job_id")
//	private int job_id;

	@OneToOne
	@JoinColumn(name="job_id")
	private Job jobObj;
	
//	@Id
//	@Column(name="candidate_id")
//	private int candidate_id;
	
	@OneToOne
	@JoinColumn(name="candidate_id")
	private Candidate candidateObj;
	
//	@Id
//	@Column(name="company_id")
//	private int company_id;
	
	
	
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
	
	public Job getJob()
	{
		return this.jobObj;
	}
	
	public void setJob(Job job)
	{
		this.jobObj = job;
	}
	
	public Candidate getCandidate()
	{
		return this.candidateObj;
	}
	
	public void setCandidate(Candidate candidate)
	{
		this.candidateObj = candidate;
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

}
