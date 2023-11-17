package com.cg.iba.util;

import org.springframework.stereotype.Component;

import com.cg.iba.dto.AdminRequestSubmitDTO;
import com.cg.iba.dto.AdminResponseDTO;
import com.cg.iba.entity.Admin;

@Component
public class AdminDTOMapper {

	public Admin setAdminUsingDTO(AdminRequestSubmitDTO dto)
	{
		Admin a = new Admin();
		
		a.setAdminContact(dto.getAdminContact());
		a.setAdminEmailId(dto.getAdminEmailId());
		a.setAdminName(dto.getAdminName());
		
		return a;
	}
	
	public AdminResponseDTO getAdminUsingDTO(Admin a) {

		AdminResponseDTO dto = new AdminResponseDTO();

		dto.setAdminId(a.getAdminId());
		dto.setAdminContact(a.getAdminContact());
		dto.setAdminEmailId(a.getAdminEmailId());
		dto.setAdminName(a.getAdminName());

		return dto;
	}
	
}
