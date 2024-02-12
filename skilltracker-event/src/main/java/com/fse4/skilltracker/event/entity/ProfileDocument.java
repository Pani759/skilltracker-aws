package com.fse4.skilltracker.event.entity;




import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "PROFILES")
public class ProfileDocument implements Serializable {

	@Id
    private String associateId;

    private String name;
    private Long mobile;

    private String email;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date lastupdated; 

    List < SkillDocument > profileSkills = new ArrayList < > ();


	@Override
	public String toString() {
		return "ProfileDocument [associateId=" + associateId + ", name=" + name + ", mobile=" + mobile + ", email="
				+ email + ", createdAt=" + createdAt + ", lastupdated=" + lastupdated + ", profileSkills="
				+ profileSkills + "]";
	}  
    
}
