package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Application;
import com.example.demo.model.Candidate;
import com.example.demo.model.Job;

public interface ApplicationDAO extends JpaRepository<Application, Integer> {

	List<Application> findJobObjByCandidateObj(Candidate cand);
	
	List<Application> findByCandidateObjNotIn(Candidate cand);
	
	List<Application> findUniqueJobObjByCandidateObjNotIn(Candidate cand);
	
}
