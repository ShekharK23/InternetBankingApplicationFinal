package com.cg.iba.util;

import org.springframework.stereotype.Component;

import com.cg.iba.dto.PolicyResponseDTO;
import com.cg.iba.entity.Policy;

@Component
public class PolicyResponseDTOConverter {
	public Policy setPolicyUsingDTO(PolicyResponseDTO dto) {
		Policy policy = new Policy();
		
		policy.setPolicyName(dto.getPolicyName());
		policy.setPolicyPremiumAmount(dto.getPolicyPremiumAmount());
		policy.setPolicySumAssured(dto.getPolicySumAssured());
		policy.setPolicyExpiryDate(dto.getPolicyExpiryDate());
		
		return policy;
	}
	
	public PolicyResponseDTO getPolicyUsingDTO(Policy policy) {
		PolicyResponseDTO dto = new PolicyResponseDTO();
		
		dto.setPolicyNumber(policy.getPolicyNumber());
		dto.setPolicyName(policy.getPolicyName());
		dto.setPolicyPremiumAmount(policy.getPolicyPremiumAmount());
		dto.setPolicySumAssured(policy.getPolicySumAssured());
		dto.setPolicyExpiryDate(policy.getPolicyExpiryDate());
		
		return dto;
	}	
}
