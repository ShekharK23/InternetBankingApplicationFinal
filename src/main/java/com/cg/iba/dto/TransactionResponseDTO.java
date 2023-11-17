package com.cg.iba.dto;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.stereotype.Component;

import com.cg.iba.entity.enums.TransactionStatus;
import com.cg.iba.entity.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDTO {

	private long transactionId;
	private double amount;

	@Enumerated(EnumType.STRING)
	@Column(name = "transactionType")
	private TransactionType transactionType;
	private String date;

	@Enumerated(EnumType.STRING)
	@Column(name = "transactionStatus")
	private TransactionStatus transactionStatus;

	private String transactionRemarks;
}
