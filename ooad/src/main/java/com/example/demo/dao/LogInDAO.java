package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Login;

public interface LogInDAO extends JpaRepository<Login, String>{

}
