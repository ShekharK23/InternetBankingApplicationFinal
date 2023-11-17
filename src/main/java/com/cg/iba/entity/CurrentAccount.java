package com.cg.iba.entity;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentAccount extends Account{

    private double currentMinBalance=10000;
    private double currentFine=2000;

}
