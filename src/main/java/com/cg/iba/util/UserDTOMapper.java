package com.cg.iba.util;

import org.springframework.stereotype.Component;

import com.cg.iba.dto.UserRequestSubmitDTO;
import com.cg.iba.dto.UserResponseDTO;
import com.cg.iba.entity.BankUser;

@Component
public class UserDTOMapper {

	public BankUser setUserUsingDTO(UserRequestSubmitDTO dto)
	{
		BankUser u = new BankUser();
		
		u.setUserName(dto.getUserName());
		u.setPassword(dto.getPassword());
		u.setUserEmailID(dto.getEmailId());
		u.setRole(dto.getRole());
		
		return u;
	}
	
	public UserResponseDTO getUserUsingDTO(BankUser u) {

		UserResponseDTO dto = new UserResponseDTO();

		dto.setUserId(u.getUserId());
		dto.setUserName(u.getUserName());
		dto.setPassword(u.getPassword());
		dto.setEmailID(u.getUserEmailID());
		dto.setRole(u.getRole());

		return dto;
	}
}
