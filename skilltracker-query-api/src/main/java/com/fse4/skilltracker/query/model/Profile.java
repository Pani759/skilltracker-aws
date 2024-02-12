package com.fse4.skilltracker.query.model;


import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile implements Serializable {

    private String associateId;
    
    private String name;

    private Long mobile;

    private String email;

    private Skills associateSkill;
    
    private List<Skills> technicalSkills;
    
    private List<Skills> nonTechnicalSkills;

	@Override
	public String toString() {
		return "Profile [associateId=" + associateId + ", name=" + name + ", mobile=" + mobile + ", email=" + email
				+ ", technicalSkills=" + technicalSkills + ", nonTechnicalSkills=" + nonTechnicalSkills + "]";
	}    
}
