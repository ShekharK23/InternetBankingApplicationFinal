package com.cg.iba.serviceimpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.longThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.iba.dto.NomineeRequestSubmitDTO;
import com.cg.iba.entity.Nominee;
import com.cg.iba.entity.Policy;
import com.cg.iba.entity.enums.Relation;
import com.cg.iba.exception.DetailsNotFoundException;

import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.IAccountRepository;
import com.cg.iba.repository.INomineeRepository;
import com.cg.iba.service.INomineeService;

@SpringBootTest
class NomineeServiceImplTest {

	@MockBean
	INomineeRepository mockNomineeRepository;
	@Autowired
	INomineeService nomineeService;
	@Autowired
	IAccountRepository mockAccountRepository;

	@Test
	@DisplayName("To save the Nominee")
	void testAddNominee() throws InvalidDetailsException {
		Nominee inputNominee = new Nominee(1, "Shubham", "12456789123", "adhar", "9987282828", Relation.SON);
		Nominee savedNominee = new Nominee(1, "Shubham", "12456789123", "adhar", "9987282828", Relation.SON);

		when(mockNomineeRepository.save(inputNominee)).thenReturn(savedNominee);

		Nominee resultNominee = nomineeService.addNominee(inputNominee);

		assertNotNull(resultNominee);
		assertEquals(savedNominee, resultNominee);

	}

	@Test
	@DisplayName("To delete the Nominee")
	void testDeleteNominee() {
		long nomineeId = 1;
		when(mockNomineeRepository.findById(nomineeId)).thenReturn(Optional.of(new Nominee()));
		boolean isDeleted = false;
		try {
			isDeleted = nomineeService.deleteNominee(nomineeId);
		} catch (DetailsNotFoundException e) {
			fail("Unexpected exception: " + e.getMessage());
		}
		assertTrue(isDeleted);
	}

	@Test
	@DisplayName("To find nominee by id")
	void testFindNomineeById() throws DetailsNotFoundException {
		Nominee actualNominee = new Nominee(1, "Shubham", "12456789123", "adhar", "9987282828", Relation.SON);
		Nominee n = new Nominee(1, "Shubham", "12456789123", "adhar", "9987282828", Relation.SON);

		Optional<Nominee> output = Optional.of(n);
		when(mockNomineeRepository.findById((long) 1)).thenReturn(output);
		assertEquals(actualNominee, nomineeService.findNomineeById(1));
	}

	@Test
	@DisplayName("To check whether the method throws exception")
	void testFindNomineeByIdException_1() {
		when(mockNomineeRepository.findById(3L)).thenThrow(IllegalArgumentException.class);
		assertThrows(IllegalArgumentException.class, ()->{
			nomineeService.findNomineeById(3L);
		});
	}
	
	@Test
	@DisplayName("Update the nominee Details")
	void testUpdateNominee() throws InvalidDetailsException, DetailsNotFoundException {
		long id = 1L;
        NomineeRequestSubmitDTO updatedNomineeDTO = new NomineeRequestSubmitDTO();
        updatedNomineeDTO.setName("Updated Name");
        updatedNomineeDTO.setGovtId("Updated GovtId");
        updatedNomineeDTO.setGovtIdType("Updated GovtIdType");
        updatedNomineeDTO.setPhoneNo("Updated PhoneNo");

        Nominee existingNominee = new Nominee();
        existingNominee.setNomineeId(1);
        existingNominee.setName("Old Name");
        existingNominee.setGovtId("Old GovtId");
        existingNominee.setGovtIdType("Old GovtIdType");
        existingNominee.setPhoneNo("Old PhoneNo");
        
        when(mockNomineeRepository.findById(1L)).thenReturn(Optional.of(existingNominee));
        when(mockNomineeRepository.save(any(Nominee.class))).thenReturn(existingNominee);

        Nominee updatedNominee = nomineeService.updateNominee(id, updatedNomineeDTO);
    
        assertNotNull(updatedNominee);
        assertEquals(id, updatedNominee.getNomineeId());
        assertEquals(updatedNomineeDTO.getName(), updatedNominee.getName());
        assertEquals(updatedNomineeDTO.getGovtId(), updatedNominee.getGovtId());
        assertEquals(updatedNomineeDTO.getGovtIdType(), updatedNominee.getGovtIdType());
        assertEquals(updatedNomineeDTO.getPhoneNo(), updatedNominee.getPhoneNo());

		
	}

}
