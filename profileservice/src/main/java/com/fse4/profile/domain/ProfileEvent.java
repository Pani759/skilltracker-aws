package com.fse4.profile.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileEvent {

	private enum ProfileStatusEnum {CREATED, UPDATED, DELETED};
	private ProfileStatusEnum  operationStatus;	

	private String message;
	private ProfileTO profileTO;	

}
