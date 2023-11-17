package com.cg.iba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.cg.iba.entity.Policy;


@Repository
public interface IPolicyRepository extends JpaRepository<Policy, Long>{
	
}
