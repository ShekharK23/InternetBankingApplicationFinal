package com.cg.iba.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cg.iba.entity.Beneficiary;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidDetailsException;

@Service
public interface IBeneficiaryService {

	public Beneficiary addBeneficiary(Beneficiary beneficiary)throws InvalidDetailsException;
	
	public boolean deleteBeneficiary(long beneficiaryId) throws DetailsNotFoundException;
	public Beneficiary findBeneficiaryById(long beneficiaryId) throws DetailsNotFoundException;
	public List<Beneficiary> listAllBeneficiariesbyAccount(long accountid) throws InvalidAccountException,EmptyListException;
}
