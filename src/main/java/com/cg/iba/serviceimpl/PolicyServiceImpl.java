package com.cg.iba.serviceimpl;

import java.time.LocalDate;	

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.iba.entity.Policy;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.IPolicyRepository;
import com.cg.iba.service.IPolicyService;
import com.cg.iba.util.date.PolicyDateConverter;

@Service
public class PolicyServiceImpl implements IPolicyService {
	
	@Autowired
	IPolicyRepository policyRepository;

	@Override
	public Policy savePolicy(Policy policy) throws InvalidDetailsException  {
		Policy savedPolicy = policyRepository.save(policy);
		if(savedPolicy != null)
			return savedPolicy;
		throw new InvalidDetailsException("Details of Policy provided are not valid", PolicyServiceImpl.class+"");
	}

	@Override
	public Policy getPolicyByPolicyNumber(long policyNumber) throws DetailsNotFoundException {
		Policy savedPolicy = policyRepository.findById(policyNumber)
				.orElseThrow(()-> new DetailsNotFoundException("You Have Entered Wrong Policy Number "+policyNumber, PolicyServiceImpl.class+""));
		return savedPolicy;
	}

	@Override
	public List<Policy> getAllPolicies() throws EmptyListException{
		List<Policy> allPolicies = policyRepository.findAll();
		if(allPolicies.isEmpty()) {
			throw new EmptyListException("No Policies Found", PolicyServiceImpl.class+"");
		}
		return allPolicies;
	}

	@Override
	@Transactional
	public Policy updatePremiumAmountOfPolicyByPolicyNumber(long policyNumber, int newPremiumAmount) throws DetailsNotFoundException {
		Policy savedPolicy = getPolicyByPolicyNumber(policyNumber);
		if(savedPolicy != null) {
			savedPolicy.setPolicyPremiumAmount(newPremiumAmount);
			return savedPolicy;
		}
		throw new DetailsNotFoundException("Policy Not Found With Policy Number "+policyNumber, PolicyServiceImpl.class+"");
	}

	@Override
	public String checkExpiryDate(long policyNumber) throws DetailsNotFoundException {
		try {
			Policy savedPolicy = getPolicyByPolicyNumber(policyNumber);
			if(savedPolicy!=null) {
				LocalDate currentDate = LocalDate.now();
				LocalDate expiryDate = PolicyDateConverter.getDateFromString(savedPolicy.getPolicyExpiryDate());
				
				if(currentDate.isAfter(expiryDate)) {
					return "Policy is Expired";
				}		
			}
			return "Policy is yet to Expired";
		}
		catch (DetailsNotFoundException e) {
			throw new DetailsNotFoundException("Policy not Found with Policy Number "+policyNumber, PolicyServiceImpl.class+"");
		}
	}
	
	@Override
	@Transactional
	public void deletePolicy(long policyNumber) throws DetailsNotFoundException{
		Policy savedPolicy = policyRepository.findById(policyNumber).orElseThrow(()-> new DetailsNotFoundException("Policy not Found with Policy Number "+policyNumber, PolicyServiceImpl.class+""));
		policyRepository.delete(savedPolicy);
	}

}
