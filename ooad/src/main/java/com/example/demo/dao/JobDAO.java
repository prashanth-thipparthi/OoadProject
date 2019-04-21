package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Job;


public interface JobDAO extends JpaRepository<Job, Long>{

}
