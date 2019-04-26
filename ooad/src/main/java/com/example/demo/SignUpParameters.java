/**
 * @author ${Prashanth Thipparthi}
 */
package com.example.demo;

/*
 * This class represents the parameters of the request body while signing up into the job portal
 */

public class SignUpParameters {
	
	private String username;
	private String password;
	private String name;
	private boolean flag;
	
	/*Getters and Setters*/
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	

	
}
