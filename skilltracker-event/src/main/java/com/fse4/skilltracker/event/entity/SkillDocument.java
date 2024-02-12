package com.fse4.skilltracker.event.entity;



import java.io.Serializable;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "SKILLS")
public class SkillDocument implements Serializable {

    @Transient
    public static final String SEQUENCE_NAME = "skill_sequence";
    
    @MongoId
    private Long id; 
    private String associateId;
    private String expertiseLevel;
    private String skillCode;
    private String skillName;
    
	@Override
	public String toString() {
		return "SkillDocument [associateId=" + associateId + ", expertiseLevel=" + expertiseLevel + ", skillCode=" + skillCode
				+ ", skillName=" + skillName + "]";
	}    

}
