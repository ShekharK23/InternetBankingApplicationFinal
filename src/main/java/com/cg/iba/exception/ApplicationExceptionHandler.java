package com.cg.iba.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ExceptionResponse> exceptionNullPointer(NullPointerException e){
		
		ExceptionResponse response = new ExceptionResponse(e.getMessage(), e.getCause().toString());
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler
	public ResponseEntity<ExceptionResponse> exceptionDetailsNotFound(DetailsNotFoundException e){
		System.err.println("Inside application error details not found");
		ExceptionResponse response = new ExceptionResponse("Wrong Input By User ",e.getSrcResName()+"");
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler
	public ResponseEntity<ExceptionResponse> exceptionEmptyList(EmptyListException e){
		
		ExceptionResponse response = new ExceptionResponse(e.getErrorMsg(),e.getSrcResName());
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler
	public ResponseEntity<ExceptionResponse> exceptionInvalidAccount(InvalidAccountException e){
		
		ExceptionResponse response = new ExceptionResponse(e.getErrorMsg(),e.getSrcResName());
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler
	public ResponseEntity<ExceptionResponse> exceptionInvalidAmount(InvalidAmountException e){
		
		ExceptionResponse response = new ExceptionResponse(e.getErrorMsg(),e.getSrcResName());
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler
	public ResponseEntity<ExceptionResponse> exceptionInvalidDetails(InvalidDetailsException e){
		
		ExceptionResponse response = new ExceptionResponse(e.getErrorMsg(),e.getSrcResName());
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler
	public ResponseEntity<ExceptionResponse> exceptionLowBalance(LowBalanceException e){
		
		ExceptionResponse response = new ExceptionResponse(e.getErrorMsg(),e.getSrcResName());
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
		
	}
	
}
