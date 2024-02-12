package com.fse4.skill.model;




import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@JsonIgnoreProperties( value = {"createdAt", "lastupdated"},  allowGetters = true )
@Table(name = "SKILL")
public class Skill implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  id;
    
    @Size(min = 5, max = 30)    
    @Column(nullable = false) 
    private String associateId;

    @Column(nullable = false)
    private String skillName;

    @Column(nullable = false)
    @Range(min = 0, max = 20)    
    private Double expertiseLevel;

    @Column(nullable = false)
    @Size(min = 4, max = 8)    
    private String skillCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private Date lastupdated;

	@Override
	public String toString() {
		return "Skill [id=" + id + ", associateId=" + associateId + ", skillName=" + skillName + ", expertiselevel="
				+ expertiseLevel + ", skillCode=" + skillCode + ", createdAt=" + createdAt + ", lastupdated="
				+ lastupdated + "]";
	}    
    
    
    
}
