/**
 * @author Amith Gopal/Akriti Kapur
 */
package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Company;
import com.example.demo.model.Job;

/*
 * This class contains functions which performs CRUD operations on the Job table in the database
 */

public interface JobDAO extends JpaRepository<Job, Long> {
	
	/*
	 * Finds the jobs which are not in the list of the requested job ids
	 */
	List<Job> findByJobIdNotIn(List<Long> ids);
	
	/*
	 * Finds the jobs which are in the list of the requested job ids
	 */
	List<Job> findByJobId(List<Long> ids);
	
	/*
	 * Finds the jobs filtered by the Company object
	 */
	List<Job> findByCompanyObj(Company compObj);
}
