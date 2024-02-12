package com.fse4.skilltracker.event.config;

import com.fse4.skilltracker.event.model.Profile;
import com.fse4.skilltracker.event.model.Skills;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KafkaMessage {


    private String mongoOpsCode;
    private Profile profile;
    private Skills skills;
	@Override
	public String toString() {
		return "KafkaMessage [mongoOpsCode=" + mongoOpsCode + ", profile=" + profile + ", skills=" + skills + "]";
	}    

    
}
