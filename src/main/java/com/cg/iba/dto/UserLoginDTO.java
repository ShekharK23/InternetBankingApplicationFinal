package com.cg.iba.dto;

import org.springframework.stereotype.Component;

import com.cg.iba.entity.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

	private String userName;
	private String password;
	private Role role;
	
}
