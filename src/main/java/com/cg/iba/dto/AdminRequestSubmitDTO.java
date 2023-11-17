package com.cg.iba.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequestSubmitDTO {
	@NotNull(message="Admin Name Cannot be null")
	private String adminName;
	@Size(max=10,message="You've Crossed the maximum contact limit")
	@Size(min = 10, message = "Minimum 10 numbers should be included in Mobile Number")
	private String adminContact;
	private String adminEmailId;
	
}
