package com.cg.iba.entity;

import java.time.LocalDate;	


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.cg.iba.entity.enums.TransactionType;
import com.cg.iba.util.date.TransactionDateConverter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.cg.iba.entity.enums.TransactionStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Details about Account Bean")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "Unique Transaction number for transaction performed")
    private long transactionId;
    private double amount;
    
	@Enumerated(EnumType.STRING)
	@Column(name="transactionType")
	@ApiModelProperty(notes = "Specify the type of transaction DEBIT or CREDIT")
    private TransactionType transactionType; 
    private String date = TransactionDateConverter.convertLocalDateToString(LocalDate.now());
    
    
	@Enumerated(EnumType.STRING)
	@Column(name="transactionStatus")
	@ApiModelProperty(notes = "Transaction type whether it is sucessfull or not ")
    private TransactionStatus transactionStatus;
	@ApiModelProperty(notes = "Remark of current Account")
    private String transactionRemarks;
   
}
