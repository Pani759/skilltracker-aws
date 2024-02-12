package com.fse4.skill.domain;




import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
public class SkillTO implements Serializable {

    private Integer  id;	
    @Size(min = 5, max = 30)    
    @NotNull(message = "Associate ID cannot be empty") 
    private String associateId;

    @NotNull(message = "Skill Name cannot be empty")
    @Size(min = 1, max = 30)    
    private String skillName;

    @NotNull(message = "Skill expertise level cannot be empty")
    @Range(min = 0, max = 20)    
    private Double expertiseLevel;

    @Size(min = 4, max = 8)    
    private String skillCode;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date lastupdated;

	@Override
	public String toString() {
		return "SkillTO [id=" + id + ", associateId=" + associateId + ", skillName=" + skillName + ", expertiselevel="
				+ expertiseLevel + ", skillCode=" + skillCode + ", createdAt=" + createdAt + ", lastupdated="
				+ lastupdated + "]";
	}


    
}
