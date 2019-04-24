package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Job;


public interface JobDAO extends JpaRepository<Job, Long>{
	List<Job> findByJobIdNotIn(List<Long> ids);
	List<Job> findByJobId(List<Long> ids);
}
