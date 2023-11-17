package com.cg.iba.entity;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavingsAccount extends Account {

	private double savingMinBalance = 2000;
	private double savingFine = 500;

}
