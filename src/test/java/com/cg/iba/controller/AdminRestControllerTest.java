package com.cg.iba.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.aspectj.weaver.PoliceExtensionUse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cg.iba.dto.PolicyResponseDTO;
import com.cg.iba.entity.Nominee;
import com.cg.iba.service.IPolicyService;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AdminRestControllerTest {
	@Autowired
	MockMvc mockmvc;
	
	@Autowired
	AdminRestController adminController;
	
	@MockBean
	IPolicyService policyService;
	
	private PolicyResponseDTO input;
	private PolicyResponseDTO output;
	
	
	void setUp() {
		input=new PolicyResponseDTO(1, "HDFC", 2, 20000, "2023-12-18");
		output=new PolicyResponseDTO(1, "HDFC", 2, 20000, "2023-12-18");
	}
	
//	@Test
//	void testSavePolicy() {
//		when(policyService.a(any(Nominee.class))).thenReturn(n);
//		mockmvc.perform(MockMvcRequestBuilders.post("/addNominee").contentType(MediaType.APPLICATION_JSON)
//				.content("{\r\n" + "    \"govtId\": \"12456789123\",\r\n" + "    \"govtIdType\": \"adhar\",\r\n"
//						+ "    \"name\": Shubham,\r\n" + "    \"phoneNo\": \"9987282828\",\r\n"
//						+ "     \"relation\":\"SON\"\r\n" + "\r\n" + "}"));
//	}

}
