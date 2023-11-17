package com.cg.iba.dto;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebitCardResponseDTO {
    private long debitCardNumber;
    private int debitCardPin;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private int debitCardLimit;
}
