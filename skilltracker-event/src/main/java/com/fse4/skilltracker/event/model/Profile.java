package com.fse4.skilltracker.event.model;


import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Profile implements Serializable {

	
    private String associateId;

    private String name;
    private Long mobile;

    private String email;

    private Date createdAt;

    private Date lastupdated;

	@Override
	public String toString() {
		return "Profile [associateId=" + associateId + ", name=" + name + ", mobile=" + mobile + ", email=" + email
				+ ", createdAt=" + createdAt + ", lastupdated=" + lastupdated + "]";
	} 
    
    

}
