package com.cg.iba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.cg.iba.entity.BankUser;

@Repository
public interface IUserRepository extends JpaRepository<BankUser, Long>{

	BankUser getBankUserByUserName(String username);

	BankUser getBankUserByUserEmailID(String email);
	
}
