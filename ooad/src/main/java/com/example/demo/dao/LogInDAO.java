/**
 * @author ${Prashanth Thipparthi}
 */
package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Login;

/*
 * This class contains functions which performs CRUD operations on the LogIn table in the database
 */


public interface LogInDAO extends JpaRepository<Login, String>{

}
