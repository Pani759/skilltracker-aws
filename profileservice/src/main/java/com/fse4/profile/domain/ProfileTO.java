package com.fse4.profile.domain;


import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileTO implements Serializable {

	
    @Size(min = 5, max = 30)    
    @NotNull(message = "Associate ID cannot be empty")    
    private String associateId;
    
    @Size(min = 5, max = 30)    
	@NotNull(message = "Associate name cannot be empty")    
    private String name;

	@NotNull(message = "Mobile Number cannot be empty")
    private Long mobile;

	@Email(message = "Email is not valid", 
			regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
	@NotNull(message = "Email cannot be empty")	
    private String email;

	@Override
	public String toString() {
		return "ProfileTO [associateId=" + associateId + ", name=" + name + ", mobile=" + mobile + ", email=" + email
				+ "]";
	}

}
