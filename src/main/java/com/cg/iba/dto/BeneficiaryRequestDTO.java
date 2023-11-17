package com.cg.iba.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.cg.iba.entity.enums.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeneficiaryRequestDTO {
	@NotNull(message="Beneficiary Name cannot be null")
	private String beneficiaryName;
	@NotNull(message="Account Number cannot be null")
	private long  beneficiaryAccNo;
	@NotNull(message="Plese Provide the IFSC code")
	private String IFSC;
	private AccountType accountType;
}
