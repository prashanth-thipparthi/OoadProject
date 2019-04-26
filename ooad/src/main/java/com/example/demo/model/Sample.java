/**
 * @author ${Prashanth Thipparthi}
 */

package com.example.demo.model;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * This class is the Object Relational Mapper (ORM) for the Sample table in the database
 * THIS IS JUST A DUMMY TABLE WHICH WAS USED TO DO SOME TESTING AND VALIDATION
 */

@XmlRootElement
@Entity
@Table(name = "sample")
public class Sample {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sample_id;
	
	@OneToOne
	@JoinColumn(name="company_id")
	private Company companyObj;

	public void setSampleId(int sid)
	{
		this.sample_id = sid;
	}
	
	public Company getCompanyObj() {
		return companyObj;
	}


	public void setCompanyObj(Company comp) {
		this.companyObj = comp;
	}
	
	
	
}
