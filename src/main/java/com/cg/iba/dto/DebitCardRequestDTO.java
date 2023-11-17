package com.cg.iba.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebitCardRequestDTO {
	@NotNull(message = "Debit pin cannot be null")
	@Size(min = 0, message = "Debit Card Pin cannot be null.")
	@Size(max = 6, message = "Debit Card Pin cannot extend 6 digits.")
	private int debitCardPin;
	private LocalDate debitCardExpiryDate;
	private int debitCardLimit;
}
