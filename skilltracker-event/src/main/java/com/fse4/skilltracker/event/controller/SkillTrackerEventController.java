package com.fse4.skilltracker.event.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.fse4.skilltracker.event.model.Profile;
import com.fse4.skilltracker.event.model.Skills;
import com.fse4.skilltracker.event.service.SkillTrackerEventService;


@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/sendEventMessage")
public class SkillTrackerEventController {

    @Autowired
    SkillTrackerEventService service;
    
    @PostMapping("/addProfile")
    public ResponseEntity<String> addProfileEvent (@RequestBody Profile addProfile)  {
    	System.out.println("IN addProfile kafka post Commmand event: "+addProfile.toString());
    	service.publishProfileAddEvent(addProfile);
        return ResponseEntity.ok("Profile added: "+addProfile.getAssociateId());

    }
    
    @PutMapping("/updateProfile")
    public ResponseEntity<String> updateProfileEvent (@RequestBody Profile updateProfile) {
    	System.out.println("IN updateProfile kafka post Commmand event: "+updateProfile.toString());    	
    	service.publishProfileUpdateEvent(updateProfile);
    	return ResponseEntity.ok("Profile updated: "+updateProfile.getAssociateId());

    } 
    
    @DeleteMapping("/deleteProfile")
    public ResponseEntity<String> deleteProfileEvent (@RequestBody Profile deleteProfile) {
    	System.out.println("IN deleteProfile kafka post Commmand event: "+deleteProfile.toString());    	
    	service.publishProfileDeleteEvent(deleteProfile);
    	return ResponseEntity.ok("Profile deleted: "+deleteProfile.getAssociateId());

    }     
    
    @PostMapping("/addSkill")
    public ResponseEntity<String> addSkillEvent (@RequestBody Skills skill) throws HttpClientErrorException {
    	System.out.println("IN addSkill kafka post Commmand event: "+skill.toString());    	
    	service.publishSkillAddEvent(skill);
        return ResponseEntity.ok("Skill Added to Mongo DB: "+skill.getAssociateId());

    }
    
    @PutMapping("/updateSkill")
    public ResponseEntity<String> updateSkillEvent (@RequestBody Skills skill) {
    	service.publishSkillUpdateEvent(skill);
    	return ResponseEntity.ok("Skill updated to Mongo DB: "+skill.getAssociateId());

    }     

    @DeleteMapping("/deleteSkill")
    public ResponseEntity<String> deleteSkillEvent (@RequestBody Skills skill) {
    	service.publishSkillDeleteEvent(skill);
    	return ResponseEntity.ok("Skill deleted from Mongo DB: "+skill.getAssociateId());

    }
}
