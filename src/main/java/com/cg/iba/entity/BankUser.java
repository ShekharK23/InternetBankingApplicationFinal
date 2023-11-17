package com.cg.iba.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.cg.iba.entity.enums.Role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Details about User Bean")
public class BankUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(notes = "Unique UserId of User")
	private long userId;
	@ApiModelProperty(notes = "Unique User Name of User")
	private String userName;
	@ApiModelProperty(notes = "Unique Email Id of User")
	private String userEmailID;
	@ApiModelProperty(notes = "Unique User Password of User")
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name="role")
	private Role role;

}
