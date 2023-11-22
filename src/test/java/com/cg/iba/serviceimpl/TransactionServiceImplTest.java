package com.cg.iba.serviceimpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.iba.entity.Nominee;
import com.cg.iba.entity.Transaction;

import com.cg.iba.entity.enums.TransactionStatus;
import com.cg.iba.entity.enums.TransactionType;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.repository.ITransactionRepository;
import com.cg.iba.service.ITransactionService;

@SpringBootTest
class TransactionServiceImplTest {
	@MockBean
	ITransactionRepository mockTransactionRepository;

	@Autowired
	ITransactionService transactionService;

	@Test
	@DisplayName("find transaction by id")
	void testFindTransactionById() throws DetailsNotFoundException {
		Transaction actual = new Transaction(1, 20000, TransactionType.DEBIT, "2023-12-11",
				TransactionStatus.SUCCESSFUL, "Done");

		Transaction t = new Transaction(1, 20000, TransactionType.DEBIT, "2023-12-11", TransactionStatus.SUCCESSFUL,
				"Done");
		Optional<Transaction> output = Optional.of(t);
		when(mockTransactionRepository.findById(1L)).thenReturn(output);
		Transaction expected = transactionService.findTransactionById(1);
		assertEquals(actual.getAmount(), expected.getAmount());
	}

}
