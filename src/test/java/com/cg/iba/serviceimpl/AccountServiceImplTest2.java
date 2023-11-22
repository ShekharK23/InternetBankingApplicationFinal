package com.cg.iba.serviceimpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.iba.entity.Account;
import com.cg.iba.entity.Beneficiary;
import com.cg.iba.entity.SavingsAccount;
import com.cg.iba.entity.Transaction;
import com.cg.iba.entity.BankUser;
import com.cg.iba.entity.enums.TransactionType;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidAmountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.exception.LowBalanceException;
import com.cg.iba.repository.IAccountRepository;
import com.cg.iba.repository.IBeneficiaryRepository;
import com.cg.iba.repository.ITransactionRepository;
import com.cg.iba.repository.IUserRepository;
import com.cg.iba.service.IAccountService;

@SpringBootTest
class AccountServiceImplTest2 {
	@MockBean
	IAccountRepository accountRepository;
	@MockBean
	ITransactionRepository transactionRepository;

	@MockBean
	IUserRepository userRepository;

	@MockBean
	IBeneficiaryRepository beneficiaryRepository;

	@Autowired
	IAccountService accountService;

	@Test
	void testTransferMoney() throws LowBalanceException, InvalidAccountException, InvalidDetailsException {
		// Mock user
		BankUser mockUser = new BankUser();
		mockUser.setUserId(1L);
		mockUser.setPassword("password");

		// Mock sender account
		SavingsAccount mockSenderAccount = new SavingsAccount();
		mockSenderAccount.setAccountId(1L);
		mockSenderAccount.setBalance(1000.0);
		mockSenderAccount.setSavingMinBalance(100.0);

		// Mock receiver account
		Beneficiary mockReceiverAccount = new Beneficiary();
		mockReceiverAccount.setBeneficiaryId(1);

		// Mock transaction
		Transaction mockTransaction = new Transaction();
		mockTransaction.setTransactionId(1L);

		// Mock repository responses
		when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(mockUser));
		when(accountRepository.findById(1L)).thenReturn(java.util.Optional.of(mockSenderAccount));
		when(beneficiaryRepository.findById(2L)).thenReturn(java.util.Optional.of(mockReceiverAccount));
		when(transactionRepository.save(any(Transaction.class))).thenReturn(mockTransaction);

		// Perform the test
		Transaction result = accountService.transferMoney(1L, 2L, 500.0, 1L, "password");

		// Assertions
		assertEquals(mockTransaction, result);
		assertEquals(500.0, mockSenderAccount.getBalance());
	}

	void testDeposit() throws InvalidAccountException, InvalidAmountException, InvalidDetailsException {

		SavingsAccount mockExistingAccount = new SavingsAccount();
		mockExistingAccount.setAccountId(1L);
		mockExistingAccount.setBalance(1000.0);
		mockExistingAccount.setSavingMinBalance(100.0);

		Transaction mockTransaction = new Transaction();
		mockTransaction.setTransactionId(1L);

		when(accountRepository.findById(1L)).thenReturn(java.util.Optional.of(mockExistingAccount));
		when(transactionRepository.save(any(Transaction.class))).thenReturn(mockTransaction);

		Transaction result = accountService.deposit(1L, 500.0, new Transaction());

		assertNotNull(result);
		assertEquals(TransactionType.CREDIT, result.getTransactionType());
		assertEquals(1500.0, mockExistingAccount.getBalance());
	}
}
