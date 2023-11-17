package com.cg.iba.util;

import org.springframework.stereotype.Component;

import com.cg.iba.dto.DebitCardResponseDTO;
import com.cg.iba.entity.DebitCard;

@Component
public class DebitCardResponseDTOConverter {

    public DebitCardResponseDTO convertToDTO(DebitCard debitCard) {
        DebitCardResponseDTO responseDTO = new DebitCardResponseDTO();

        responseDTO.setDebitCardNumber(debitCard.getDebitCardNumber());
        responseDTO.setDebitCardPin(debitCard.getDebitCardPin());
        responseDTO.setIssueDate(debitCard.getIssueDate());
        responseDTO.setExpiryDate(debitCard.getDebitCardExpiryDate());
        responseDTO.setDebitCardLimit(debitCard.getDebitCardLimit());
        return responseDTO;
    }
}

