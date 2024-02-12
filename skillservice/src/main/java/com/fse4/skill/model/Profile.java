package com.fse4.skill.model;




import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

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
@Table(name = "PROFILE")
public class Profile implements Serializable {

    @Id
    @Size(min = 5, max = 30)    
    @Column(nullable = false) 
    private String associateId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long mobile;

    @Column(nullable = false)
    private String email;

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
		return "Profile [associateId=" + associateId + ", name=" + name + ", mobile=" + mobile + ", email=" + email
				+ ", createdAt=" + createdAt + ", lastupdated=" + lastupdated + "]";
	}    
    
    
}
