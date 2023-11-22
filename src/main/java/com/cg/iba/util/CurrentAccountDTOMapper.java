package com.cg.iba.util;

import org.springframework.stereotype.Component;

import com.cg.iba.dto.CurrentAccountRequestSubmitDTO;
import com.cg.iba.dto.CurrentAccountResponseDTO;
import com.cg.iba.entity.CurrentAccount;

@Component
public class CurrentAccountDTOMapper {

	public CurrentAccount setCurrentAccountUsingDTO(CurrentAccountRequestSubmitDTO dto)
	{
		CurrentAccount a = new CurrentAccount();
		
		a.setAccountHolderName(dto.getAccountHolderName());
		a.setPhoneNo(dto.getPhoneNo());
		a.setEmailId(dto.getEmailId());
		a.setAge(dto.getAge());
		a.setGender(dto.getGender());
		a.setBalance(dto.getBalance());
		a.setDateOfOpening(dto.getDateOfOpening());
		
		return a;
	}

	public CurrentAccountResponseDTO getCurrentAccountUsingDTO(CurrentAccount a) {

		CurrentAccountResponseDTO dto = new CurrentAccountResponseDTO();
		dto.setAccountId(a.getAccountId());
		dto.setAccountHolderName(a.getAccountHolderName());
		dto.setPhoneNo(a.getPhoneNo());
		dto.setEmailId(a.getEmailId());
		dto.setAge(a.getAge());
		dto.setGender(a.getGender());
		dto.setInterestRate(a.getInterestRate());
		dto.setBalance(a.getBalance());
		dto.setDateOfOpening(a.getDateOfOpening());
		dto.setAccountStatus(a.getAccountStatus());
		dto.setCurrentMinBalance(a.getCurrentMinBalance());
		dto.setCurrentFine(a.getCurrentFine());

		return dto;
	}
}
