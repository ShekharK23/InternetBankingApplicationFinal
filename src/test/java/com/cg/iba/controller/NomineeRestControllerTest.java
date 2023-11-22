package com.cg.iba.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.cg.iba.dto.NomineeRequestSubmitDTO;
import com.cg.iba.dto.NomineeResponseDTO;
import com.cg.iba.entity.Account;
import com.cg.iba.entity.Nominee;
import com.cg.iba.entity.enums.Relation;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.serviceimpl.NomineeServiceImpl;
import com.cg.iba.util.NomineeDTOMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class NomineeRestControllerTest {

	@Autowired
	MockMvc mockmvc;

	@Autowired
	NormalUserController normalController;
	
	@Autowired
	CustomerRestController customerRestController;

	@Autowired
	NomineeDTOMapper nomineeDTOMapper;

	@MockBean
	NomineeServiceImpl mockNomineeService;

	private NomineeRequestSubmitDTO input;
	private NomineeRequestSubmitDTO output;

	@BeforeEach
	void setup() {
		input = new NomineeRequestSubmitDTO("Shubham", "12456789123", "adhar", "9987282828", Relation.SON);

		output = new NomineeRequestSubmitDTO("Shubham", "12456789123", "adhar", "9987282828", Relation.SON);
	}

	@Test
	@DisplayName("Test to check registration of new nominee")
	void testAddNominee() throws Exception {
		Nominee n = nomineeDTOMapper.setNomineeUsingDTO(input);
		when(mockNomineeService.addNominee(any(Nominee.class))).thenReturn(n);
		mockmvc.perform(MockMvcRequestBuilders.post("/addNominee").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "    \"govtId\": \"12456789123\",\r\n" + "    \"govtIdType\": \"adhar\",\r\n"
						+ "    \"name\": Shubham,\r\n" + "    \"phoneNo\": \"9987282828\",\r\n"
						+ "     \"relation\":\"SON\"\r\n" + "\r\n" + "}"));
	}

	@Test
	@DisplayName("Test to find out Nominee with id")
	void testFindNomineeById() throws DetailsNotFoundException {
		Nominee n = new Nominee(1, "Shubham", "12456789123", "adhar", "9987282828", Relation.SON);
		NomineeResponseDTO dto = nomineeDTOMapper.getNomineeUsingDTO(n);
		when(mockNomineeService.findNomineeById(1L)).thenReturn(n);
		ResponseEntity<NomineeResponseDTO> actualOutput = normalController.findNomineeById(1);
		NomineeResponseDTO actualDTO = actualOutput.getBody();
		assertEquals(dto, actualDTO);
	}

//	@Test
//	@DisplayName("Test Update the Nominee")
//	void testUpdateNominee() throws Exception {
//		long nomineeId = 1L;
//		Nominee savedNominee = nomineeDTOMapper.setNomineeUsingDTO(output);
//		  when(mockNomineeService.updateNominee(eq(nomineeId), any(NomineeRequestSubmitDTO.class))).thenReturn(savedNominee);
//
//		    // Perform the request
//		    mockmvc.perform(MockMvcRequestBuilders.put("/updateNomineeDetails/{nomineeId}", nomineeId)
//		            .contentType(MediaType.APPLICATION_JSON)
//		            .content("{\r\n" +
//		                    "    \"govtId\": \"12456789123\",\r\n" +
//		                    "    \"govtIdType\": \"adhar\",\r\n" +
//		                    "    \"name\": \"Shubham\",\r\n" + 
//		                    "    \"phoneNo\": \"9987282828\",\r\n" +
//		                    "    \"relation\":\"SON\"\r\n" +
//		                    "}\r\n"))
//		            .andExpect(MockMvcResultMatchers.status().isOk()); 
//
//		    // Verify the interactions
//		    verify(mockNomineeService, times(1)).updateNominee(eq(nomineeId), any(NomineeRequestSubmitDTO.class));
//		}


	@Test
	@DisplayName("Test To get all the nominees")
	void testListAllNominees() throws InvalidAccountException, EmptyListException {
		Account account = new Account();
		account.setAccountId(1);
		Nominee nominee_1 = new Nominee(2, null, null, null, null, null);
		Nominee nominee_2 = new Nominee(23, null, null, null, null, null);
 
		List<Nominee> mockNominees = Arrays.asList(nominee_1, nominee_2);
		when(mockNomineeService.listAllNominees(1L)).thenReturn(mockNominees);
 
		ResponseEntity<List<NomineeResponseDTO>> actual = normalController.listAllNominees(1L);
		List<NomineeResponseDTO> actualDto = actual.getBody();
 
		List<NomineeResponseDTO> expectedDto = new ArrayList<>();
		for (Nominee nominee : mockNominees) {
			expectedDto.add(nomineeDTOMapper.getNomineeUsingDTO(nominee));
		}
 
		assertEquals(expectedDto, actualDto);
	}
////	@Test
////	void testDeleteNominee() throws Exception {
////		long nomineeId = 1;
////        when(mockNomineeService.deleteNominee(nomineeId)).thenReturn(true);
////
////        mockmvc.perform(MockMvcRequestBuilders.delete("/deleteNomineeById/{nomineeId}", nomineeId))
////                .andExpect(MockMvcResultMatchers.status().isOk())
////                .andExpect(MockMvcResultMatchers.content().string("The Nominee is deleted for the id-:" + nomineeId));
////		
//	}
}
