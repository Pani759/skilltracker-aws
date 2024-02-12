package com.fse4.skill.exception;

public class SkillNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public SkillNotFoundException(String message){
    	super(message);
    }
}
