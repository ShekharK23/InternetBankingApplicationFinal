package com.cg.iba.dto;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.cg.iba.entity.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingAccountResponseDTO {
	private long accountId;
	private String accountHolderName;
    private String phoneNo;
    private String emailId;
    private int age;
    private Gender gender; 
    private double interestRate;
    private double balance;
    private LocalDate dateOfOpening;
    
    private double savingMinBalance;
    private double savingFine; 
    
}
