package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Candidate;
import com.example.demo.model.Login;

public interface CandidateDAO extends JpaRepository<Candidate, Integer> {

//	@Query("FROM candidates c WHERE c.username = :userObject")
//	List<Candidate> findCandidates(@Param("userObject") Login userObj);
	//Candidate findByUserName(String userName);

	Candidate findByUser(Login user);
}
