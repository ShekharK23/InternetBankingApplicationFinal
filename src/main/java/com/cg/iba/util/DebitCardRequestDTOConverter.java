package com.cg.iba.util;

import org.springframework.stereotype.Component;

import com.cg.iba.dto.DebitCardRequestDTO;
import com.cg.iba.entity.DebitCard;

@Component
public class DebitCardRequestDTOConverter {

    public DebitCard convertToEntity(DebitCardRequestDTO requestDTO) {
        DebitCard debitCard = new DebitCard();
        
        debitCard.setDebitCardPin(requestDTO.getDebitCardPin());
        debitCard.setDebitCardLimit(requestDTO.getDebitCardLimit());
        debitCard.setDebitCardStatus(requestDTO.getDebitCardStatus());
     
        return debitCard;
    }
}

