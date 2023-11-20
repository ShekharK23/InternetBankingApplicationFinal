package com.cg.iba.serviceimpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.iba.entity.DebitCard;
import com.cg.iba.exception.DetailsNotFoundException;
import com.cg.iba.exception.InvalidDetailsException;
import com.cg.iba.repository.IDebitCardRepository;

@SpringBootTest
class DebitCardServiceImplTest {
	
	@MockBean
	IDebitCardRepository debitCardRepository;
	
	@Autowired
	DebitCardServiceImpl debitCardServiceImpl;

	@Test
	void testSaveDebitCardDetails() throws InvalidDetailsException {
		DebitCard debitCard = new DebitCard();
		when(debitCardRepository.save(debitCard)).thenReturn(debitCard);
        long newDebitCardNumber = debitCardServiceImpl.saveDebitCardDetails(debitCard);
        assertNotNull(newDebitCardNumber);
        assertEquals(debitCard.getDebitCardNumber(), newDebitCardNumber);
	}

	@Test
	void testGetDebitCardByDebitCardNumber() throws DetailsNotFoundException {
		long debitCardNumber = 1234;
        DebitCard mockDebitCard = new DebitCard();
        mockDebitCard.setDebitCardNumber(debitCardNumber);
        
        when(debitCardRepository.findById(debitCardNumber)).thenReturn(Optional.of(mockDebitCard));
        
        DebitCard retrievedDebitCard = debitCardServiceImpl.getDebitCardByDebitCardNumber(debitCardNumber);
        assertEquals(debitCardNumber, retrievedDebitCard.getDebitCardNumber());
	}

	@Test
	void testChangePin() {
		long debitCardNumber = 1234;
        DebitCard mockedDebitCard = new DebitCard();
        mockedDebitCard.setDebitCardNumber(debitCardNumber);

        when(debitCardRepository.findById(debitCardNumber)).thenReturn(Optional.of(mockedDebitCard));
        when(debitCardRepository.save(any(DebitCard.class))).thenReturn(mockedDebitCard);

        int newPin = 5678;
        try {
            DebitCard updatedDebitCard = debitCardServiceImpl.changePin(debitCardNumber, newPin);

            assertNotNull(updatedDebitCard);
            assertEquals(newPin, updatedDebitCard.getDebitCardPin());
        } catch (DetailsNotFoundException e) {
            fail("Unexpected DetailsNotFoundException");
        }
	}

}
