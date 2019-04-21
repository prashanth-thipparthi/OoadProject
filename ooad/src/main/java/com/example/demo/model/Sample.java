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
