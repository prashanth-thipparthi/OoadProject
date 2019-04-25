package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Company;
import com.example.demo.model.Login;
import java.util.List;

public interface CompanyDAO extends  JpaRepository<Company, Integer>  {
	
	Company findByUser(Login user);

}
