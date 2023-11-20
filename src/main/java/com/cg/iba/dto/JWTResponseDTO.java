package com.cg.iba.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTResponseDTO {
	
	private String token;
	private String username;
	private boolean isValid;
	private long userId;
	
}
