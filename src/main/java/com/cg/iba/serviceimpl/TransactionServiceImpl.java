package com.cg.iba.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.iba.entity.Account;
import com.cg.iba.entity.CurrentAccount;
import com.cg.iba.entity.SavingsAccount;
import com.cg.iba.entity.Transaction;
import com.cg.iba.entity.enums.TransactionStatus;
import com.cg.iba.entity.enums.TransactionType;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.exception.LowBalanceException;
import com.cg.iba.repository.IAccountRepository;
import com.cg.iba.repository.ITransactionRepository;
import com.cg.iba.service.IAccountService;
import com.cg.iba.service.ITransactionService;

@Service
public class TransactionServiceImpl implements ITransactionService {
	@Autowired
	ITransactionRepository transactionRepository;

	@Autowired
	IAccountRepository accountRepository;

	@Autowired
	IAccountService accountService;

	@Override
	public Transaction createTransaction(Transaction transaction) throws InvalidDetailsException {
		Transaction newTransaction = transactionRepository.save(transaction);
		if (newTransaction != null) {
			return newTransaction;
		} else
			throw new InvalidDetailsException("Please fill all the required field in order to create a Transaction",
					TransactionServiceImpl.class + "");
	}

	@Override
	public Transaction findTransactionById(long transaction_id) throws DetailsNotFoundException {
		Transaction pastTransaction = transactionRepository.findById(transaction_id)
				.orElseThrow(() -> new DetailsNotFoundException("Transaction id " + transaction_id + " is not found",
						TransactionServiceImpl.class + ""));
		return pastTransaction;
	}

	@Override
	public List<Transaction> listAllTransactions(long accountId, String from, String to)
			throws InvalidAccountException, EmptyListException {
		Account existingAccount = accountRepository.findById(accountId).orElseThrow(() -> new InvalidAccountException(
				"The Account with the id  " + accountId + " is not found ", TransactionServiceImpl.class + ""));

		List<Transaction> transactions = existingAccount.getTransactions().stream().filter(transaction -> {
			String transactionDate = transaction.getDate();
			return transactionDate.compareTo(from) >= 0 && transactionDate.compareTo(to) <= 0;
		}).collect(Collectors.toList());
		if (!transactions.isEmpty()) {
			return transactions;
		} else
			throw new EmptyListException("You've not done any TRANSACTION within stipulated Time Range",
					TransactionServiceImpl.class + "");

	}

	@Override
	public List<Transaction> getAllMyAccTransactions(long account_id)
			throws InvalidAccountException, EmptyListException {
		Account existingAccount = accountRepository.findById(account_id).orElseThrow(() -> new InvalidAccountException(
				"The Account with the id  " + account_id + " is not found ", TransactionServiceImpl.class + ""));
		List<Transaction> allMyTransactions = existingAccount.getTransactions();
		if (!allMyTransactions.isEmpty()) {
			return allMyTransactions;
		} else {
			throw new EmptyListException("You've not performed any transaction", TransactionServiceImpl.class + "");
		}
	}

	@Override
	@Transactional
	public Transaction withdraw(long accounId, double amount, Transaction t)
			throws InvalidAccountException, LowBalanceException, InvalidDetailsException {
		Account existingAccount = accountRepository.findById(accounId).orElseThrow(() -> new InvalidAccountException(
				"The Account with the id  " + accounId + " is not found ", TransactionServiceImpl.class + ""));
		if (existingAccount instanceof SavingsAccount) {
			SavingsAccount savings = (SavingsAccount) existingAccount;
			if (existingAccount.getBalance() < savings.getSavingMinBalance()) {
				throw new LowBalanceException("You Should maintain Minimum balance of " + savings.getSavingMinBalance(),
						TransactionServiceImpl.class + "");
			} else {
				if (amount <= 0) {
					throw new InvalidDetailsException("Amount less than or equal to zero cannot be entered",
							AccountServiceImpl.class + "");
				}
				existingAccount.setBalance(existingAccount.getBalance() - amount);
				t.setAmount(amount);
				t.setTransactionType(TransactionType.DEBIT);
				t.setTransactionStatus(TransactionStatus.SUCCESSFUL);
				transactionRepository.save(t);
				accountRepository.save(existingAccount);
				accountService.addTransactionToAccount(t.getTransactionId(), accounId);
				return t;
			}

		} else if (existingAccount instanceof CurrentAccount) {
			CurrentAccount currentAcc = (CurrentAccount) existingAccount;
			if (existingAccount.getBalance() < currentAcc.getCurrentMinBalance()) {
				throw new LowBalanceException(
						"You Should maintain Minimum balance of " + currentAcc.getCurrentMinBalance(),
						TransactionServiceImpl.class + "");
			} else {
				if (amount <= 0) {
					throw new InvalidDetailsException("Amount less than or equal to zero cannot be entered",
							AccountServiceImpl.class + "");
				}
				existingAccount.setBalance(existingAccount.getBalance() - amount);
				t.setAmount(amount);
				t.setTransactionType(TransactionType.DEBIT);
				t.setTransactionStatus(TransactionStatus.SUCCESSFUL);
				transactionRepository.save(t);

				accountRepository.save(existingAccount);
				accountService.addTransactionToAccount(t.getTransactionId(), accounId);
				return t;
			}

		}
		return null;

	}
}
