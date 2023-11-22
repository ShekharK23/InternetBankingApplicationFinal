package com.cg.iba.dto;

import org.springframework.stereotype.Component;

import com.cg.iba.entity.enums.AccountStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountStatusUpdateDTO {
	
	private AccountStatus accountStatus;
	
}
