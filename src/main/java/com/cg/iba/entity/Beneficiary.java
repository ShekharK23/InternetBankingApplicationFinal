package com.cg.iba.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.cg.iba.entity.enums.AccountType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Details about Beneficiary Bean")
public class Beneficiary {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "Unique Beneficiary Id of Beneficiary")
	private long  beneficiaryId;
	@ApiModelProperty(notes = "Unique Beneficiary Name of Beneficiary")
	private String beneficiaryName;
	@ApiModelProperty(notes = "Unique Beneficiary Account Number of Beneficiary")
	private long  beneficiaryAccNo;
	@ApiModelProperty(notes = "Unique Ifsc of Beneficiary")
	private String IFSC;

	@Enumerated(EnumType.STRING)
	@Column(name="accountType1")
	private AccountType accountType;

}
