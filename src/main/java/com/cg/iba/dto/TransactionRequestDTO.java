package com.cg.iba.dto;

import javax.persistence.Column;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.cg.iba.entity.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {

	@Enumerated(EnumType.STRING)
	@Column(name = "transactionStatus")
	@NotNull(message = "Transaction status Should not be null")
	private TransactionStatus transactionStatus;

	@NotNull(message = "Please fill the transaction Remark")
	@NotBlank(message = "Transaction Remark cannot be Blank")
	@Size(min = 3, message = "Remark should be of minimum 3 chracters")
	private String transactionRemarks;
}
