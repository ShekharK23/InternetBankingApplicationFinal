package com.cg.iba.service;

import org.springframework.stereotype.Service;

import com.cg.iba.entity.DebitCard;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.InvalidDetailsException;

@Service
public interface IDebitCardService {
	
	public long saveDebitCardDetails(DebitCard debitCard) throws InvalidDetailsException;
	public DebitCard getDebitCardByDebitCardNumber(long debitCardNumber)throws DetailsNotFoundException;
	public DebitCard changePin(long debitCardNumber, int newPin)throws DetailsNotFoundException;
	public DebitCard requestNewCard(long debitCardNumber);
	public String checkExpiry(long debitCardNumber)throws DetailsNotFoundException;
	public DebitCard blockCard(long debitCardNumber, String cardStatus)throws DetailsNotFoundException;
}