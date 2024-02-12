package com.fse4.skilltracker.query.model;

import java.util.Objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Skills {

    private String skillCode;
    
	private String skillName;
	
    private Double rating;
    
	public Skills(String skillName, String skillCode) {
		this.skillCode= skillCode;
		this.skillName = skillName;
	}
    
	
    @Override
	public String toString() {
		return "Search Profile Skills [skillCode=" + skillCode + ", skillName=" + skillName + ", rating=" + rating + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(skillCode, skillName);
	}

	@Override
	public boolean equals(Object obj) {
		Skills other = (Skills) obj;
		return Objects.equals(skillCode, other.getSkillCode()) || Objects.equals(skillName, other.getSkillName());
	}

    

}
