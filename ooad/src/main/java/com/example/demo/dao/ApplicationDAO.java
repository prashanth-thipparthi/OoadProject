package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Application;

public interface ApplicationDAO extends CrudRepository<Application, Integer> {

}
