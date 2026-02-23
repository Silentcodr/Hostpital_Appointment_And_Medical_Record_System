package com.hospital.record.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hospital.record.system.dto.ResponseStructure;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
		
	@ExceptionHandler(IdNotFoundException.class)
	ResponseEntity<ResponseStructure<String>> HandleINFE(IdNotFoundException exception)
	{
		ResponseStructure<String> response = new ResponseStructure<>();
		response.setData("Failure");
		response.setMessage(exception.getMessage());
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RecordNotFoundException.class)
	ResponseEntity<ResponseStructure<String>> HandleINFE(RecordNotFoundException exception)
	{
		ResponseStructure<String> response = new ResponseStructure<>();
		response.setData("Failure");
		response.setMessage(exception.getMessage());
		response.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(response,HttpStatus.NOT_FOUND);
	}
}
