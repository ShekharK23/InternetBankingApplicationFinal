package com.cg.iba.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseDTO {

	private long adminId;
	private String adminName;
	private String adminContact;
	private String adminEmailId;
	
}
