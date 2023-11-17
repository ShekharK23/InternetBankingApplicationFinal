package com.cg.iba.util;

import org.springframework.stereotype.Component;

import com.cg.iba.dto.SavingAccountRequestSubmitDTO;
import com.cg.iba.dto.SavingAccountResponseDTO;
import com.cg.iba.entity.SavingsAccount;

@Component
public class SavingsAccountDTOMapper {

	public SavingsAccount setSavingAccountUsingDTO(SavingAccountRequestSubmitDTO dto)
	{
		SavingsAccount a = new SavingsAccount();
		
		a.setAccountHolderName(dto.getAccountHolderName());
		a.setPhoneNo(dto.getPhoneNo());
		a.setEmailId(dto.getEmailId());
		a.setAge(dto.getAge());
		a.setGender(dto.getGender());
//		a.setInterestRate(dto.getInterestRate());
		a.setBalance(dto.getBalance());
		a.setDateOfOpening(dto.getDateOfOpening());
//		a.setSavingMinBalance(dto.getSavingMinBalance());
//		a.setSavingFine(dto.getSavingFine());
		
		return a;
	}
	
	public SavingAccountResponseDTO getSavingAccountUsingDTO(SavingsAccount a)
	{
		SavingAccountResponseDTO dto = new SavingAccountResponseDTO();
		dto.setAccountId(a.getAccountId());
		dto.setAccountHolderName(a.getAccountHolderName());
		dto.setPhoneNo(a.getPhoneNo());
		dto.setEmailId(a.getEmailId());
		dto.setAge(a.getAge());
		dto.setGender(a.getGender());
		dto.setInterestRate(a.getInterestRate());
		dto.setBalance(a.getBalance());
		dto.setDateOfOpening(a.getDateOfOpening());
		dto.setSavingMinBalance(a.getSavingMinBalance());
		dto.setSavingFine(a.getSavingFine());
		
		return dto;
	}
}
