package com.cg.iba.serviceimpl;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.iba.entity.Account;
import com.cg.iba.entity.DebitCard;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.IDebitCardRepository;
import com.cg.iba.service.IDebitCardService;

@Service
public class DebitCardServiceImpl implements IDebitCardService {

	@Autowired
	IDebitCardRepository debitCardRepository;

	@Override
	public long saveDebitCardDetails(DebitCard debitCard) throws InvalidDetailsException {
		// Set the issue date before saving
		debitCard.setIssueDate(LocalDate.now());
		DebitCard savedDebitCard = debitCardRepository.save(debitCard);
		if (savedDebitCard != null) {
			long newDebitCardNumber = savedDebitCard.getDebitCardNumber();
			return newDebitCardNumber;
		}
		throw new InvalidDetailsException("Invalid debit card details", DebitCardServiceImpl.class + "");
	}

	@Override
	public DebitCard getDebitCardByDebitCardNumber(long debitCardNumber) throws DetailsNotFoundException {
		DebitCard savedDebitCard = debitCardRepository.findById(debitCardNumber)
				.orElseThrow(()-> new DetailsNotFoundException("Debit Card not found", DebitCardServiceImpl.class + ""));
		return savedDebitCard;
	}

	@Override
	@Transactional
	public DebitCard changePin(long debitCardNumber, int newPin) throws DetailsNotFoundException {
		DebitCard savedDebitCard = getDebitCardByDebitCardNumber(debitCardNumber);
		if (savedDebitCard != null) {
			savedDebitCard.setDebitCardPin(newPin);
			return savedDebitCard;
		} else {
			throw new DetailsNotFoundException("Debit Card not found", DebitCardServiceImpl.class + "");
		}
	}

	@Transactional
	@Override
	public DebitCard blockCard(long debitCardNumber, String cardStatus) throws DetailsNotFoundException {
		DebitCard savedDebitCard = getDebitCardByDebitCardNumber(debitCardNumber);
		if (savedDebitCard != null) {
			savedDebitCard.setDebitCardStatus(cardStatus);
			if (cardStatus.equalsIgnoreCase("Not Allocated")) {
				savedDebitCard.setDebitCardStatus(cardStatus);
			}
			return savedDebitCard;
		} else {
			throw new DetailsNotFoundException("Debit Card not found", DebitCardServiceImpl.class + "");
		}
	}

	@Override
	public String checkExpiry(long debitCardNumber) throws DetailsNotFoundException {
		DebitCard savedDebitCard = getDebitCardByDebitCardNumber(debitCardNumber);
		if (savedDebitCard != null) {
			LocalDate currentDate = LocalDate.now();
			LocalDate expiryDate = savedDebitCard.getDebitCardExpiryDate();

			if (expiryDate != null && currentDate.isAfter(expiryDate)) {
				return "Debit Card is Expired";
			}
			return "Debit Card is Yet To Expired";
		} else {
			throw new DetailsNotFoundException("Debit Card not found", DebitCardServiceImpl.class + "");
		}
	}

	@Override
	public DebitCard requestNewCard(long debitCardNumber) {
		DebitCard existingDebitCard = debitCardRepository.findById(debitCardNumber).get();
		if (existingDebitCard != null) {
			DebitCard newDebitCard = new DebitCard();

			DebitCard savedNewDebitCard = debitCardRepository.save(newDebitCard);
			if (savedNewDebitCard != null) {
				return savedNewDebitCard;
			}
			Account acc = new Account();

			acc.setDebitCard(savedNewDebitCard);
		}
		return null;
	}
}
