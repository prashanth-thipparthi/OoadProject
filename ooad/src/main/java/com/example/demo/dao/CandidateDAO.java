/**
 * @author Amith Gopal
 */
package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Candidate;
import com.example.demo.model.Login;

/*
 * This class contains functions which performs CRUD operations on the Candidate table in the database
 */

public interface CandidateDAO extends JpaRepository<Candidate, Integer> {


	/*
	 * Finds the Candidate with a requested Login object user
	 */
	Candidate findByUser(Login user);
}
