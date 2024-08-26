package com.vir.exceptionhandler;

import java.util.HashMap;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.vir.exception.EmployeeNotFoundException;

@ControllerAdvice
public class CustomExceptionHandler {
	
	
	  @ExceptionHandler(Exception.class) public ResponseEntity<String>
	  handleException(Exception ex) {
	  
	  System.err.println("Exception occurred: " + ex.getMessage()); return new
	  ResponseEntity<>("An error occurred: " + ex.getMessage(),
	  HttpStatus.INTERNAL_SERVER_ERROR); 
	  }
	 
	  @ExceptionHandler(AccessDeniedException.class)
	    @ResponseStatus(HttpStatus.FORBIDDEN)
	    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
	        return ResponseEntity
	                .status(HttpStatus.FORBIDDEN)
	                .body("Access Denied: You do not have permission to access this resource.");
	    }
	
	 @ExceptionHandler(BadCredentialsException.class)
	 public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex) {
		 return ResponseEntity
				 .status(HttpStatusCode.valueOf(401))
				 .body("Authentication Failed: Bad Credentials");
	 }
	  
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map <String, String> handleInvalidArgument(MethodArgumentNotValidException ex){
		Map<String, String> errorMap = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errorMap.put(error.getField(), error.getDefaultMessage());
		});
		return errorMap;
	}
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map <String, String> handleBusinessException(EmployeeNotFoundException ex){
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("errorMessage", ex.getMessage());
		return errorMap;
		
	}

}
