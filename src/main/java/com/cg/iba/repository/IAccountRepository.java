package com.cg.iba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.cg.iba.entity.Account;
import com.cg.iba.entity.enums.AccountStatus;


@Repository
public interface IAccountRepository extends JpaRepository<Account, Long>{

	Account findByUserUserId(long userId);
	List<Account> findByAccountStatus(AccountStatus status);
	
}
