package com.cg.iba.entity;

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
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Details about Policy Bean")
public class Policy {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "Unique Policy Number of Policy")
	private long policyNumber;
	@ApiModelProperty(notes = "Unique Policy Name of Policy")
	private String policyName;
	@ApiModelProperty(notes = "Unique Policy Premium Amount of Policy")
	private int policyPremiumAmount;
	@ApiModelProperty(notes = "Unique Policy Sum Assured of Policy")
	private int policySumAssured;
	@ApiModelProperty(notes = "Unique Policy Expired Date of Policy")
	private String policyExpiryDate;
	
}
