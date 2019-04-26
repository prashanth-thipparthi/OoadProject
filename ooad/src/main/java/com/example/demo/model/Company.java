/**
 * @author ${Prashanth Thipparthi}
 */
package com.example.demo.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * This class is the Object Relational Mapper (ORM) for the companies table in the database
 */

@XmlRootElement
@Entity(name = "companies")
@Table(name = "companies")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id")
	@Column(name="company_id")
	private int company_id;
	
	@JsonProperty("name")
	@Column(name="companyname")
	private String companyname;
	
	@Column(name="description")
	private String description;
	
	@OneToOne
	@JoinColumn(name="username")
	private Login user;
	
	public Company() {}
	
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Login getUser() {
		return user;
	}
	public void setUser(Login user) {
		this.user = user;
	}
	
}
