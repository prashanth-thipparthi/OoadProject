/**
 * @author Amith Gopal
 */
package com.example.demo;

/*
 * This class represents the parameters of the request body while adding a job to the Job table to the database.
 */

public class AddJobParameters {

	private String skills;
	private String location;
	private String description;
	private String role;
	private int company_id;
	
	/* Getters and Setters */
	
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}
	
}
