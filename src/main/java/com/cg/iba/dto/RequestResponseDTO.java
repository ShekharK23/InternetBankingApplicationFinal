package com.cg.iba.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestResponseDTO {

	private long requestId;
	private long accountId; 
	private String accountHolderName;
    private String phoneNo;
    private String requestType;
    private String status;
    
}
