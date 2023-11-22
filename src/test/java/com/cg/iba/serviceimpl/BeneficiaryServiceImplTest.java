package com.cg.iba.serviceimpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.iba.entity.Beneficiary;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.IBeneficiaryRepository;
import com.cg.iba.service.IBeneficiaryService;

@SpringBootTest
class BeneficiaryServiceImplTest {
	
	@Autowired
	private IBeneficiaryService beneficiaryService;
	
	@MockBean
	private IBeneficiaryRepository beneficiaryRepository;
	
	

	@Test
	void testAddBeneficiary() throws InvalidDetailsException {
		Beneficiary b = new Beneficiary();
		b.setBeneficiaryAccNo(12);
		Mockito.when(beneficiaryRepository.save(any(Beneficiary.class))).thenReturn(b);
				Beneficiary result = beneficiaryService.addBeneficiary(b);
		 
				assertNotNull(result);
				assertEquals(12, result.getBeneficiaryAccNo());
	}

	@Test
	void testFindBeneficiaryById() throws DetailsNotFoundException {
		Long beneficiryId = 1L;
		Beneficiary b = new Beneficiary();
	    b.setBeneficiaryId(beneficiryId);
		
		when(beneficiaryRepository.findById(beneficiryId)).thenReturn(Optional.of(b));
		
		Beneficiary appResult = beneficiaryService.findBeneficiaryById(beneficiryId);
		
		assertNotNull(appResult);
		assertEquals(b, appResult);
	}
//
//	@Test
//	void testListAllBeneficiariesbyAccount() {
//		fail("Not yet implemented");
//	}

}
