package com.cg.iba.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.aspectj.weaver.PoliceExtensionUse;
import org.junit.jupiter.api.BeforeEach;
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
import com.cg.iba.entity.Policy;
import com.cg.iba.service.IPolicyService;
import com.cg.iba.util.PolicyResponseDTOConverter;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AdminRestControllerTest {
	@Autowired
	MockMvc mockmvc;

	@Autowired
	AdminRestController adminController;

	@MockBean
	IPolicyService policyService;

	@Autowired
	PolicyResponseDTOConverter policyResponseDTOConverter;

	private PolicyResponseDTO input;
	private PolicyResponseDTO output;

	@BeforeEach
	void setUp() {
		input = new PolicyResponseDTO(1, "HDFC", 2, 20000, "2023-12-18");
		output = new PolicyResponseDTO(1, "HDFC", 2, 20000, "2023-12-18");
	}

	@Test
	void testSavePolicy() throws Exception {

		Policy p = policyResponseDTOConverter.setPolicyUsingDTO(input);
		when(policyService.savePolicy(any(Policy.class))).thenReturn(p);
		mockmvc.perform(MockMvcRequestBuilders.post("/policy/save").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "    \"policyNumber\": \"1\",\r\n" + "    \"policyName\": \"HDFC\",\r\n"
						+ "    \"policyPremiumAmount\": 2,\r\n" + "    \"policySumAssured\": \"20000\",\r\n"
						+ "     \"policyExpiryDate\":\"12\"\r\n" + "\r\n" + "}"));
	}

}
