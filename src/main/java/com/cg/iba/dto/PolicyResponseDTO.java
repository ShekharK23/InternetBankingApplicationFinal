package com.cg.iba.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyResponseDTO {
	@NotNull(message = "policy number cannot be null")
	private long policyNumber;
	@NotNull(message = "Policy Name should not be null")
	private String policyName;
	@Min(value = 0,message = "You cannot enter values less than 0")
	private int policyPremiumAmount;
	@Min(value = 0,message = "You cannot enter values less than 0")
	private int policySumAssured;
	private String policyExpiryDate;
}
