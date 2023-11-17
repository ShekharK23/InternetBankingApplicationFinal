package com.cg.iba.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cg.iba.entity.Transaction;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.EmptyListException;
import com.cg.iba.exception.InvalidAccountException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.exception.LowBalanceException;

@Service
public interface ITransactionService {

	public Transaction createTransaction(Transaction transaction) throws InvalidDetailsException;
	public Transaction findTransactionById(long transaction_id) throws DetailsNotFoundException;
	public  List<Transaction> listAllTransactions(long accountId,String from, String to) throws
													InvalidAccountException,EmptyListException;
	public  List<Transaction> getAllMyAccTransactions(long account_id) throws 
							InvalidAccountException,EmptyListException;
	
	public Transaction withdraw(long accounId, double amount,Transaction t) throws InvalidAccountException,LowBalanceException,InvalidDetailsException;
}
