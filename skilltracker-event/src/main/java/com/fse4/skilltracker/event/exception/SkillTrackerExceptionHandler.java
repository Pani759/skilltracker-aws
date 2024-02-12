/**
 * 
 */
package com.fse4.skilltracker.event.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author cogjava759
 *
 */
@ControllerAdvice
public class SkillTrackerExceptionHandler extends RuntimeException {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ExceptionHandler(RecordNotFoundException.class)
	  public ResponseEntity<ErrorResponse> resourceNotFoundException(RecordNotFoundException exception) {
		  ErrorResponse message = new ErrorResponse(HttpStatus.NOT_FOUND,new Date(), exception.getMessage());
	    
	    return new ResponseEntity<>(message,HttpStatus.OK);
	  }
	  
	  @ExceptionHandler(SkillAlreadyExistsException.class)
	  public ResponseEntity<ErrorResponse> associateSkillFoundException(SkillAlreadyExistsException exception) {
		  ErrorResponse message = new ErrorResponse(HttpStatus.FOUND,new Date(), exception.getMessage());
	    
	    return new ResponseEntity<>(message,HttpStatus.OK);
	  }	  

	  @ExceptionHandler(SyncToReadDatabaseFailed.class)
	  public ResponseEntity<ErrorResponse> syncToReadDatabaseFailedException(SyncToReadDatabaseFailed exception) {
		  ErrorResponse message = new ErrorResponse(HttpStatus.EXPECTATION_FAILED,new Date(), exception.getMessage());
	    
	    return new ResponseEntity<>(message,HttpStatus.OK);
	  }	  
	  
	  @ExceptionHandler(ProfileUpdateNotAllowedException.class)
	  public ResponseEntity<ErrorResponse> associateProfileUpdateNotAllowedException(ProfileUpdateNotAllowedException exception) {
		  ErrorResponse message = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE,new Date(), exception.getMessage());
	    
	    return new ResponseEntity<>(message,HttpStatus.OK);
	  }	  	  
	  
	  @ExceptionHandler(Exception.class)
	  public ResponseEntity<ErrorResponse> globalExceptionHandler(Exception exception) {
		  ErrorResponse message = new ErrorResponse(HttpStatus.BAD_REQUEST,new Date(), exception.getMessage());
	    
	    return new ResponseEntity<>(message,HttpStatus.OK);	    
	  }	  
}
