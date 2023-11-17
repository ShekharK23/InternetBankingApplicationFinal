package com.cg.iba.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="admins_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Details about Admin Bean")

public class Admin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "Unique AdminId of Admin")
	private long adminId;
	@ApiModelProperty(notes = "Unique Admin Name of Admin")
	private String adminName;
	@ApiModelProperty(notes = "Unique Admin Contact of Admin")
	private String adminContact;
	@ApiModelProperty(notes = "Unique Admin EmailId of Admin")
	private String adminEmailId;
	
    @OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userID")
	private BankUser user;

}
