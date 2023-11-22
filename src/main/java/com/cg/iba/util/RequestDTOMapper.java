package com.cg.iba.util;

import org.springframework.stereotype.Component;

import com.cg.iba.dto.RequestResponseDTO;
import com.cg.iba.dto.RequestSubmitDTO;
import com.cg.iba.entity.Request;

@Component
public class RequestDTOMapper {

	public Request setRequestUpdateUsingDTO(RequestSubmitDTO dto)
	{
		Request r = new Request();
		
		r.setAccountHolderName(dto.getAccountHolderName());
		r.setPhoneNo(dto.getPhoneNo());
		r.setAccountId(dto.getAccountId());
		r.setRequestType(dto.getRequestType());;
		r.setStatus(dto.getStatus());
	
		return r;
	}
	
	public RequestResponseDTO getRequestUsingDTO(Request r) {

		RequestResponseDTO dto = new RequestResponseDTO();
		
		dto.setRequestId(r.getRequestId());
		dto.setAccountId(r.getAccountId());
		dto.setAccountHolderName(r.getAccountHolderName());
		dto.setPhoneNo(r.getPhoneNo());
		dto.setRequestType(r.getRequestType());
		dto.setStatus(r.getStatus());
		
		return dto;
	}
}
