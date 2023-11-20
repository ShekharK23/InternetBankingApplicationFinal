package com.cg.iba.serviceimpl;

import static org.junit.jupiter.api.Assertions.*;	
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.iba.entity.Policy;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.IPolicyRepository;


@SpringBootTest
class PolicyServiceImplTest {
	
	@Autowired
	PolicyServiceImpl policyService;
	
	@MockBean
	IPolicyRepository policyRepository;

	@Test
	void testSavePolicy() throws InvalidDetailsException {
		
		Policy addPolicy = new Policy();
		addPolicy.setPolicyName("LIC");
		Mockito.when(policyRepository.save(any(Policy.class))).thenReturn(addPolicy);
		Policy result = policyService.savePolicy(addPolicy);
 
		assertNotNull(result);
		assertEquals("LIC", result.getPolicyName());
	
	}

//	@Test
//	void testGetPolicyByPolicyNumber() {
//		fail("Not yet implemented");
//	}

	@Test
	void testUpdatePremiumAmountOfPolicyByPolicyNumber() throws DetailsNotFoundException {
		Policy policy = new Policy();
		policy.setPolicyExpiryDate("23-10-2023");
		when(policyRepository.findById(anyLong())).thenReturn(Optional.of(policy));
		Policy updatedPolicy = policyService.updatePremiumAmountOfPolicyByPolicyNumber(1, 1);
		assertNotNull(updatedPolicy);
		assertEquals("23-10-2023",updatedPolicy.getPolicyExpiryDate());
	}

}
