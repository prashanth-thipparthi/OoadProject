/*
 * author Prashanth Thipparthi
 */
package com.example.demo.dao;

/*
 * This class contains functions which performs CRUD operations on the Skills table in the database
 */

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Skills;
import java.lang.String;
import java.util.List;

public interface SkillsDAO extends JpaRepository<Skills,String>{

	/* 
	 * Finds all the skills in the skills table
	 */
	List<Skills> findAll();

}
