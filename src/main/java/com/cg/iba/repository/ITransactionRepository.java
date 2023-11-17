package com.cg.iba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.iba.entity.Transaction;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {

}
