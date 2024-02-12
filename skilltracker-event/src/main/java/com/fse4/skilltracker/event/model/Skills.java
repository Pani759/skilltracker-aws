package com.fse4.skilltracker.event.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@Data
public class Skills implements Serializable{

	private Long id;
	private String associateId;
    private Double expertiseLevel;
    private String skillCode;
    private String skillName;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date lastupdated;
	@Override
	public String toString() {
		return "Skills [associateId=" + associateId + ", expertiseLevel=" + expertiseLevel + ", skillCode=" + skillCode + ", skillName="
				+ skillName + ", createdAt=" + createdAt + ", lastupdated=" + lastupdated + "]";
	}        

    
}
