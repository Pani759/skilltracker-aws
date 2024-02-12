package com.fse4.skill.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class SkillExceptionHandler extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ExceptionHandler(ProfileNotFoundException.class)
	public ResponseEntity<ErrorResponse> profileNotFoundException(ProfileNotFoundException exception) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,new Date(), exception.getMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK);
	}
	
	@ExceptionHandler(SkillNotFoundException.class)
	public ResponseEntity<ErrorResponse> resourceNotFoundException(SkillNotFoundException exception) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,new Date(), exception.getMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK);
	}
	
	@ExceptionHandler(SyncDataFailedException.class)
	public ResponseEntity<ErrorResponse> syncDataFailedException(SyncDataFailedException exception) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.EXPECTATION_FAILED,new Date(), exception.getMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK);
	}	
	
	@ExceptionHandler(SkillFoundException.class)
	public ResponseEntity<ErrorResponse> resourceFoundException(SkillFoundException exception) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FOUND,new Date(), exception.getMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK);
	}	

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> globleExcpetionHandler(Exception exception) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, new Date(), exception.getMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK );
	}
	
	  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
	      HttpStatus status, WebRequest request) {
	    Map<String, List<String>> body = new HashMap<>();
	    
	    List<String> errors = ex.getBindingResult()
	        .getFieldErrors()
	        .stream()
	        .map(DefaultMessageSourceResolvable::getDefaultMessage)
	        .collect(Collectors.toList());
	    
	    body.put("errors", errors);
	    
	    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	  }	
}
