package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Application;
import com.example.demo.model.Candidate;
import com.example.demo.model.Job;

/**
 * @author ${user}
 */

/*
 * This class contains functions which performs CRUD operations on the Application table in the database
 */

public interface ApplicationDAO extends JpaRepository<Application, Integer> {

	/*
	 * Finds Applications filtered by the Candidate Object
	 */
	List<Application> findJobObjByCandidateObj(Candidate cand);
	
	/*
	 * Finds Applications that the Candidate hasn't applied to
	 */
	List<Application> findByCandidateObjNotIn(Candidate cand);
	
	/*
	 * Finds Applications that the Candidate hasn't applied to
	 */
	List<Application> findUniqueJobObjByCandidateObjNotIn(Candidate cand);
	
	/*
	 * Finds Applications filtered by the Job object
	 */
	List<Application> findByJObjIn(Job jobs);
	
}
