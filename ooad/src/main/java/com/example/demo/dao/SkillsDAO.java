/*
 * author Prashanth Thipparthi
 */
package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Skills;
import java.lang.String;
import java.util.List;

public interface SkillsDAO extends JpaRepository<Skills,String>{
	//List<Skills> findA
	List<Skills> findAll();

}
