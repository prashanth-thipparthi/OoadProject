package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Candidate;

public interface CandidateDAO extends JpaRepository<Candidate, Integer> {

}
