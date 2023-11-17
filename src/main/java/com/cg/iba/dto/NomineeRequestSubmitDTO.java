package com.cg.iba.dto;
	
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import com.cg.iba.entity.enums.Relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NomineeRequestSubmitDTO {
	@NotNull(message="Nominee name cannot be null")
    private String name;
	@Size(min=0,message = "Please Enter proper Government Id")
	@Size(max=12,message = "Please Enter proper Government Id")
    private String govtId;
    @NotNull(message = "Government Id type cannot be null")
    private String govtIdType;
    @Size(max=10,message="Maximum 10 Numbers Should include")
    @Size(min = 10, message = "Minimum 10 numbers should be included in Mobile Number")
    private String phoneNo;
    
	@Enumerated(EnumType.STRING)
    private Relation relation; 
}
