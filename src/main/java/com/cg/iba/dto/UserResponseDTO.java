package com.cg.iba.dto;

import org.springframework.stereotype.Component;

import com.cg.iba.entity.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

	private long userId;
	private String userName;
	private String password;
	private String userEmailID;
	private Role role;
	
}
