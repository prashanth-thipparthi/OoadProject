/*
 * author - Prashanth Thipparthi
 */
package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity(name = "skills")
@Table(name = "skills")
public class Skills {
	
	@Id
	private String skill;

	public Skills() {
		
	}
	public Skills(String skill) {
		this.skill = skill;		
	}
	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}
}
