package com.fse4.skilltracker.event.model;


import org.springframework.http.HttpStatus;

import com.fse4.skilltracker.event.entity.ProfileDocument;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileEventStatus {

	public HttpStatus status;
	public String message;
	private Profile associateProfile;	

}
