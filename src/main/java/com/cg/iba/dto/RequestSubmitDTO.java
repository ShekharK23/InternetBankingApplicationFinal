package com.cg.iba.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestSubmitDTO {
	
	private long accountId; 
	private String accountHolderName;
    private String phoneNo;
    private String requestType;
    private String status;
    
}
