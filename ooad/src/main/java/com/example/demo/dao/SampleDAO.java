/**
 * @author ${user}
 */
package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Sample;

/*
 * This class contains functions which performs CRUD operations on the Sample table in the database
 */

public interface SampleDAO extends  JpaRepository<Sample, Integer>  {

}

