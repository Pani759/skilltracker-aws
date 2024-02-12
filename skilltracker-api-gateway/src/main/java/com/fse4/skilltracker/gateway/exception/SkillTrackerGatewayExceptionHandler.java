package com.fse4.skilltracker.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.MalformedJwtException;

@ControllerAdvice
public class SkillTrackerGatewayExceptionHandler extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ExceptionHandler(JwtTokenMalformedException.class)
	public ResponseEntity<String> resourceNotFoundException(JwtTokenMalformedException exception) {
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.OK);
	}
	
	@ExceptionHandler(JwtTokenMissingException.class)
	public ResponseEntity<String> profileFoundException(JwtTokenMissingException exception) {
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.OK);
	}
	

	@ExceptionHandler(MalformedJwtException.class)
	public ResponseEntity<String> globleExcpetionHandler(MalformedJwtException exception) {
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.OK );
	}
}
