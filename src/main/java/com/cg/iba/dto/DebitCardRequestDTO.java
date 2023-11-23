package com.cg.iba.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
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
	private int debitCardPin;
	private int debitCardLimit;
	private String debitCardStatus;
}
