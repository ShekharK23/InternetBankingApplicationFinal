package com.cg.iba.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
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
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateDTO {

	@NotNull(message = "Account holder name cannot be null")
	private String accountHolderName;
	@NotNull(message = "Phone number cannot be null")
	@Size(max = 10, message = "Maximum 10 numbers should be included in Mobile Number")
	private String phoneNo;
	@Email
	private String emailId;
	private int age;
	private Gender gender;

}
