package com.fse4.skilltracker.query.entity;


import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document("SKILLS")
public class SkillsDocument {

    private Double expertiseLevel;
    private String skillCode;
    private String skillName;

	@Override
	public String toString() {
		return "SkillsDocument [expertiseLevel=" + expertiseLevel + ", skillCode=" + skillCode + ", skillName="
				+ skillName + "]";
	}    
}
