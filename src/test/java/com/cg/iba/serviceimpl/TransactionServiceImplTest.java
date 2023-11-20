package com.cg.iba.serviceimpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.iba.entity.Account;
import com.cg.iba.entity.Nominee;
import com.cg.iba.entity.SavingsAccount;
import com.cg.iba.entity.Transaction;

import com.cg.iba.entity.enums.TransactionStatus;
import com.cg.iba.entity.enums.TransactionType;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidAmountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.exception.LowBalanceException;
import com.cg.iba.repository.IAccountRepository;
import com.cg.iba.repository.ITransactionRepository;
import com.cg.iba.service.IAccountService;
import com.cg.iba.service.ITransactionService;

@SpringBootTest
class TransactionServiceImplTest {
	@MockBean
	ITransactionRepository mockTransactionRepository;

	@MockBean
	IAccountRepository mockAccountRepository;

	@Autowired
	ITransactionService transactionService;

	@Autowired
	IAccountService accountService;

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

//	@Test
//	void testWithdrawFromSavingsAccount() throws InvalidAccountException, LowBalanceException, InvalidDetailsException {
//		Account uniqueAccount=new Account();
//		double amount=400;
//		uniqueAccount.setAccountId(1);
//		uniqueAccount.setBalance(25000);
//		SavingsAccount sa=new SavingsAccount(100, 500);
//		Transaction t=new Transaction(2,amount , null, null, null, "paid"); 
//		when(mockAccountRepository.findById(1L)).thenReturn(Optional.of(sa));
//		when(mockTransactionRepository.save(t)).thenReturn(t);
//		when(accountService.addTransactionToAccount(eq(t.getTransactionId()), uniqueAccount.getAccountId())).thenReturn(sa);
//		Transaction actual=transactionService.withdraw(1, 400, t);
//		
//		assertEquals(t, actual);
//		
//	}

	@Test
	@DisplayName("Testing transaction between two particular dates")
	void testListAllTransactions() throws InvalidAccountException, EmptyListException {
		Account account = new Account();
		account.setAccountId(1);
		Transaction transaction_1 = new Transaction(12, 0, null, "2023-12-18", null, null);
		Transaction transaction_2 = new Transaction(13, 0, null, "2023-12-19", null, null);

		List<Transaction> allTransactions = Arrays.asList(transaction_1, transaction_2);
		account.setTransactions(allTransactions);

		when(mockAccountRepository.findById(1L)).thenReturn(Optional.of(account));

		List<Transaction> actualTransactions = transactionService.listAllTransactions(1, "2023-12-18", "2023-12-19");
		assertEquals(allTransactions, actualTransactions);

	}

	@Test
	@DisplayName("To check wheteher the exception is working or not")
	void testListAllTransactionException()  {	
		when(mockAccountRepository.findById(2L)).thenThrow(IllegalArgumentException.class);
		assertThrows(IllegalArgumentException.class, ()->{
			mockAccountRepository.findById(2L).get();
		});
		
	}

	@Test
	void testGetAllMyAccountTransaction() throws InvalidAccountException, EmptyListException {
		Account existingAccount = new Account();
		existingAccount.setAccountId(1);
		Transaction transaction_1 = new Transaction(12, 0, null, "2023-12-18", null, null);
		Transaction transaction_2 = new Transaction(13, 0, null, "2023-12-19", null, null);

		List<Transaction> allTrasnaction = Arrays.asList(transaction_1, transaction_2);
		existingAccount.setTransactions(allTrasnaction);

		when(mockAccountRepository.findById(1L)).thenReturn(Optional.of(existingAccount));
		List<Transaction>actualTransactions=transactionService.getAllMyAccTransactions(1);
		assertEquals(allTrasnaction, actualTransactions);

	}

}
