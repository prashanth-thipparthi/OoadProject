/**
 * @author ${user}
 */
package com.example.demo;

/*
 * This class represents the parameters of the request body while adding an application
 */

public class AddApplicationParameters {

	private String job_id;
	private String candidate_id;
	private String status;
		
	/*Getters and Setters of the private variables*/
	
	public String getJob_id() {
		return job_id;
	}
	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}
	
	public String getCandidate_id() {
		return candidate_id;
	}
	public void setCandidate_id(String candidate_id) {
		this.candidate_id = candidate_id;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
