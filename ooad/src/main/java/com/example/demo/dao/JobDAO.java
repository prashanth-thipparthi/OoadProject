package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.model.Job;


public interface JobDAO extends CrudRepository<Job, Long>{

}
