package com.cg.iba.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long requestId;
	private long accountId; 
	private String accountHolderName;
    private String phoneNo;
    private String requestType;
    private String status;
    
}
