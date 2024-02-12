package com.fse4.profile.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProfileExceptionHandler extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ExceptionHandler(ProfileNotFoundException.class)
	public ResponseEntity<ErrorResponse> resourceNotFoundException(ProfileNotFoundException exception) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,new Date(), exception.getMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK);
	}
	
	@ExceptionHandler(ProfileFoundException.class)
	public ResponseEntity<ErrorResponse> profileFoundException(ProfileFoundException exception) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FOUND,new Date(), exception.getMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK);
	}
	
	@ExceptionHandler(APIGatewayURIException.class)
	public ResponseEntity<ErrorResponse> profileFoundException(APIGatewayURIException exception) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_GATEWAY,new Date(), exception.getMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK);
	}	
	
	@ExceptionHandler(InvalidMobileNumberException.class)
	public ResponseEntity<ErrorResponse> invalidMobileNumberException(InvalidMobileNumberException exception) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE,new Date(), exception.getMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK);
	}		

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> globleExcpetionHandler(Exception exception) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, new Date(), exception.getLocalizedMessage());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK );
	}
}
