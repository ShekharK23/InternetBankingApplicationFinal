package com.cg.iba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.cg.iba.entity.DebitCard;

@Repository
public interface IDebitCardRepository extends JpaRepository<DebitCard, Long>{

}
