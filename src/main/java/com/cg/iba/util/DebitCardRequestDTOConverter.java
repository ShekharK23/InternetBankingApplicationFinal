package com.cg.iba.util;

import org.springframework.stereotype.Component;

import com.cg.iba.dto.DebitCardRequestDTO;
import com.cg.iba.entity.DebitCard;

@Component
public class DebitCardRequestDTOConverter {

    public DebitCard convertToEntity(DebitCardRequestDTO requestDTO) {
        DebitCard debitCard = new DebitCard();
        
        debitCard.setDebitCardPin(requestDTO.getDebitCardPin());
        debitCard.setDebitCardExpiryDate(requestDTO.getDebitCardExpiryDate());
        debitCard.setDebitCardLimit(requestDTO.getDebitCardLimit());
     
        return debitCard;
    }
}

