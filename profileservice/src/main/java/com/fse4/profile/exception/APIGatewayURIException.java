package com.fse4.profile.exception;

public class APIGatewayURIException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public APIGatewayURIException(String message){
    	super(message);
    }
}
