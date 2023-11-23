package com.cg.iba.util;

import org.springframework.stereotype.Component;

import com.cg.iba.dto.BeneficiaryRequestDTO;
import com.cg.iba.dto.BeneficiaryResponseDTO;
import com.cg.iba.entity.Beneficiary;

@Component
public class BeneficiaryDTOMapper {

	public Beneficiary setBeneficiaryUsingDTO(BeneficiaryRequestDTO dto)
	{
		Beneficiary b = new Beneficiary();
		
		b.setAccountType(dto.getAccountType());
		b.setBeneficiaryAccNo(dto.getBeneficiaryAccNo());
		b.setBeneficiaryName(dto.getBeneficiaryName());
		b.setIFSC(dto.getIfsc());
		
		return b;
	}
	
	public BeneficiaryResponseDTO getBeneficiaryUsingDTO(Beneficiary b)
	{
		BeneficiaryResponseDTO dto = new BeneficiaryResponseDTO();
		
		dto.setAccountType(b.getAccountType());
		dto.setBeneficiaryAccNo(b.getBeneficiaryAccNo());
		dto.setBeneficiaryName(b.getBeneficiaryName());
		dto.setIfsc(b.getIFSC());
		dto.setBeneficiaryId(b.getBeneficiaryId());
		
		return dto;
	}
}
