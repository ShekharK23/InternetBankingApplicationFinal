package com.cg.iba.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;
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
public class CurrentAccountRequestSubmitDTO {
	@NotNull(message = "Account Holder name cannot be null")
	private String accountHolderName;
	@Size(max = 10, message = "Maximum 10 numbers should be included in Mobile Number")
	@Size(min = 10, message = "Minimum 10 numbers should be included in Mobile Number")
    private String phoneNo;
    private String emailId;
    private int age;
    private Gender gender; 

    @Min(value = 10000,message="Minimum 10,000 Balance Should be maintained")
    private double balance;
//    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate  dateOfOpening;
    
   
}