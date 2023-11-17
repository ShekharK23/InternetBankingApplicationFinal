package com.cg.iba.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.cg.iba.entity.enums.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Details about Nominee Bean")
public class Nominee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "Unique NomineeId of Nominee")
    private long nomineeId;
	@ApiModelProperty(notes = "Unique name of Nominee")
    private String name;
	@ApiModelProperty(notes = "Unique Government Id of Nominee")
    private String govtId; 
	@ApiModelProperty(notes = "Unique GovernmentId Type of Nominee")
    private String govtIdType; 
	@ApiModelProperty(notes = "Unique Phone Number of Nominee")
    private String phoneNo;
    
	@Enumerated(EnumType.STRING)
	@Column(name="relation")
	@ApiModelProperty(notes = "Relation of Nominee with Account Holder")
    private Relation relation; 
   
 }
