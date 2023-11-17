package com.cg.iba.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.iba.entity.Account;
import com.cg.iba.entity.Beneficiary;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.IAccountRepository;
import com.cg.iba.repository.IBeneficiaryRepository;
import com.cg.iba.service.IBeneficiaryService;

@Service
public class BeneficiaryServiceImpl implements IBeneficiaryService{
	
	@Autowired
	IBeneficiaryRepository BenefeciaryRepository;
	
	@Autowired
	IAccountRepository accountRepository;

	@Override
	public Beneficiary addBeneficiary(Beneficiary beneficiary) throws InvalidDetailsException {
	    Beneficiary b = BenefeciaryRepository.save(beneficiary);
	    return b;
	}

	
	@Override
	public boolean deleteBeneficiary(long beneficiaryId) throws DetailsNotFoundException {
		Beneficiary b = BenefeciaryRepository.findById(beneficiaryId)
				.orElseThrow(()-> new DetailsNotFoundException("Details Not Found for Given Beneficiary ID",BeneficiaryServiceImpl.class+""));
		BenefeciaryRepository.deleteById(beneficiaryId);
		return true;
	}

	@Override
	public Beneficiary findBeneficiaryById(long beneficiaryId) throws DetailsNotFoundException {
	    Beneficiary b = BenefeciaryRepository.findById(beneficiaryId)
	    		.orElseThrow(()-> new DetailsNotFoundException("Details Not Found for Given Beneficiary ID",BeneficiaryServiceImpl.class+""));
		return b;
	}

	@Override
	public List<Beneficiary> listAllBeneficiariesbyAccount(long accountid)
			throws InvalidAccountException, EmptyListException {
		Account account = accountRepository.findById(accountid)
				.orElseThrow(()-> new InvalidAccountException("Details Not Found for Given Account ID",BeneficiaryServiceImpl.class+""));
		List<Beneficiary> allBeneficiary = account.getBeneficiaries();
		if(!allBeneficiary.isEmpty()) {
			return allBeneficiary;
		} else {
			throw new EmptyListException("List of Beneficiaries is Empty.",BeneficiaryServiceImpl.class+"");
		}
	}

}
