package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Application;

public interface ApplicationDAO extends JpaRepository<Application, Integer> {

}
