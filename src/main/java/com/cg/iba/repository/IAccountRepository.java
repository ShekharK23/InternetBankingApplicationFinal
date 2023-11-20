package com.cg.iba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.cg.iba.entity.Account;


@Repository
public interface IAccountRepository extends JpaRepository<Account, Long>{

	//public Account getAccountByBankUser(long userid);
	public Account findByUserUserId(long userId);
}
