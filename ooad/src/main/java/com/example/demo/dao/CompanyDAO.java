package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Company;

public interface CompanyDAO extends  JpaRepository<Company, Integer>  {

}
