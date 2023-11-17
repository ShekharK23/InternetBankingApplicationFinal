package com.cg.iba.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvalidDetailsException extends Exception {

	private String errorMsg;
	private String  srcResName;
	
}
