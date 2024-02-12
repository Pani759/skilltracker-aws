package com.fse4.skilltracker.jwt.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fse4.skilltracker.jwt.model.response.ErrorRes;

@ControllerAdvice
public class JWTExceptionHandler extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorRes> globleExcpetionHandler(Exception exception) {
		ErrorRes errorResponse = new ErrorRes(HttpStatus.FORBIDDEN, new Date(), exception.getLocalizedMessage());
		return new ResponseEntity<ErrorRes>(errorResponse, HttpStatus.OK );
	}
	
    @ExceptionHandler(value = UnauthorisedException.class)
    public ResponseEntity<ErrorRes> handleUnauthorizedException(UnauthorisedException exception) {
    	exception.printStackTrace();
		ErrorRes errorResponse = new ErrorRes(HttpStatus.UNAUTHORIZED, new Date(), exception.getLocalizedMessage());
		return new ResponseEntity<ErrorRes>(errorResponse, HttpStatus.OK );
    }

}
