package com.cg.iba.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cg.iba.entity.Policy;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidDetailsException;

@Service
public interface IPolicyService {
	public Policy savePolicy(Policy policy) throws InvalidDetailsException ;
	public Policy getPolicyByPolicyNumber(long policyNumber) throws DetailsNotFoundException;
	public List<Policy> getAllPolicies() throws EmptyListException;
	public Policy updatePremiumAmountOfPolicyByPolicyNumber(long policyNumber, int newPremiumAmount) throws DetailsNotFoundException;
	public String checkExpiryDate(long policyNumber) throws DetailsNotFoundException;
	public void deletePolicy(long policyNumber) throws DetailsNotFoundException;
}
