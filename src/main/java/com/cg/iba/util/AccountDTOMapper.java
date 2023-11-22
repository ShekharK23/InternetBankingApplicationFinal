package com.cg.iba.util;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.cg.iba.dto.AccountRequestSubmitDTO;
import com.cg.iba.dto.AccountResponseDTO;
import com.cg.iba.entity.Account;

@Component
public class AccountDTOMapper {

	public Account setAccountUsingDTO(AccountRequestSubmitDTO dto)
	{
		Account a = new Account();
		
		a.setAccountHolderName(dto.getAccountHolderName());
		a.setPhoneNo(dto.getPhoneNo());
		a.setEmailId(dto.getAccountHolderName());
		a.setAge(dto.getAge());
		a.setGender(dto.getGender());
		a.setInterestRate(dto.getInterestRate());
		a.setBalance(dto.getBalance());
		a.setDateOfOpening(dto.getDateOfOpening());
		
		return a;
	}
	
	public AccountResponseDTO getAccountUsingDTO(Account a) {

		AccountResponseDTO dto = new AccountResponseDTO();

		dto.setAccountId(a.getAccountId());
		dto.setAccountHolderName(a.getAccountHolderName());
		dto.setPhoneNo(a.getPhoneNo());
		dto.setEmailId(a.getEmailId());
		dto.setAge(a.getAge());
		dto.setGender(a.getGender());
		dto.setInterestRate(a.getInterestRate());
		dto.setBalance(a.getBalance());
		dto.setDateOfOpening(a.getDateOfOpening().toString());
		dto.setAccountStatus(a.getAccountStatus());

		return dto;
	}
	
}
