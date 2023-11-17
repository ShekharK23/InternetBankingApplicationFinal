package com.cg.iba.util;

import org.springframework.stereotype.Component;

import com.cg.iba.dto.TransactionRequestDTO;
import com.cg.iba.dto.TransactionResponseDTO;
import com.cg.iba.entity.Transaction;

@Component
public class TransactionDTOMapper {

	public Transaction setTransactionUsingDTO(TransactionRequestDTO dto) {
		Transaction t = new Transaction();

		t.setTransactionStatus(dto.getTransactionStatus());
		t.setTransactionRemarks(dto.getTransactionRemarks());

		return t;

	}

	public TransactionResponseDTO getTransactionUsingDTO(Transaction transaction) {
		TransactionResponseDTO responseDto = new TransactionResponseDTO();
		responseDto.setTransactionId(transaction.getTransactionId());
		responseDto.setAmount(transaction.getAmount());
		responseDto.setTransactionType(transaction.getTransactionType());
		responseDto.setDate(transaction.getDate());
		responseDto.setTransactionStatus(transaction.getTransactionStatus());
		responseDto.setTransactionRemarks(transaction.getTransactionRemarks());

		return responseDto;

	}
}
