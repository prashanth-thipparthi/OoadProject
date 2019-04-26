/**
 * @author Amith Gopal
 */
package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Company;
import com.example.demo.model.Login;
import java.util.List;

/*
 * This class contains functions which performs CRUD operations on the Company table in the database
 */

public interface CompanyDAO extends  JpaRepository<Company, Integer>  {
	
	/*
	 * Finds the Company with a requested Login object user
	 */
	Company findByUser(Login user);

}
