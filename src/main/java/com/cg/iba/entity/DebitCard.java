package com.cg.iba.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Details about DebitCard Bean")
public class DebitCard {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "Unique DebitCard Number of DebitCard")
	private long debitCardNumber;
	@ApiModelProperty(notes = "Unique Issue Date of DebitCard")
	private LocalDate issueDate=LocalDate.now();
	@ApiModelProperty(notes = "Unique DebitCard Pin of DebitCard")
	private int debitCardPin;
	@ApiModelProperty(notes = "Unique DebitCard Expiry Date of DebitCard")
	private LocalDate debitCardExpiryDate;
	@ApiModelProperty(notes = "Unique DebitCard Limit of DebitCard")
	private int debitCardLimit;
	@ApiModelProperty(notes = "Unique DebitCard Status of DebitCard")
	private String debitCardStatus;

}
