package com.cg.iba.dto;

import javax.persistence.EnumType;	
import javax.persistence.Enumerated;

import org.springframework.stereotype.Component;

import com.cg.iba.entity.enums.Relation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NomineeResponseDTO {
	private long nomineeId;
	private String name;
	private String govtId;
	private String govtIdType;
	private String phoneNo;

	@Enumerated(EnumType.STRING)
	private Relation relation;
}
