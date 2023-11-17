package com.cg.iba.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.cg.iba.entity.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingAccountRequestSubmitDTO {
	
	@NotNull(message = "Account Holder name cannot be null")
	@NotBlank(message = "Account Holder name cannot be null")
	private String accountHolderName;
	@Size(max = 10, message = "Maximum 10 numbers should be included in Mobile Number")
	@Size(min = 10, message = "Minimum 10 numbers should be included in Mobile Number")
    private String phoneNo;
    private String emailId;
    @Min(value = 10,message = "Age Should be greater than 10 year")
    private int age;
    private Gender gender; 
   // private final double interestRate;
    @Min(value=2000,message="Minimum Account Balance Should be 2000")
    private double balance;
    private LocalDate dateOfOpening;
    
//    private double savingMinBalance;
//    private double savingFine; 
    
}
