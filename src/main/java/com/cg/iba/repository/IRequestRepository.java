package com.cg.iba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.iba.entity.Request;

@Repository
public interface IRequestRepository extends JpaRepository<Request, Long>{

}
