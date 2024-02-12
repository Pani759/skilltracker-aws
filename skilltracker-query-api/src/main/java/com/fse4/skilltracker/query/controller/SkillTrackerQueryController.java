package com.fse4.skilltracker.query.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fse4.skilltracker.query.model.Profile;
import com.fse4.skilltracker.query.service.SkillTrackerQueryService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@EnableEurekaClient
@RequestMapping("/skill-tracker/api/v1/admin")
public class SkillTrackerQueryController {

    @Autowired
    private SkillTrackerQueryService service;

    /**
     *
     * @return - Return Value
     */
    @GetMapping("/getAllAssociates")
    public List<Profile> getAllAssociates(){
        return service.getAllAssociates();

    }

    @GetMapping("/getAssociatesByName/{name}")
    public List<Profile> getAssociatesByName(@PathVariable String name){
        //return service.getAllAssociates();
        return service.getAssociatesByName(name);    	
    }    
    
    @GetMapping("/getAssociateByID/{associateID}")
    public Profile getAssociateByID(@PathVariable String associateID){
        return service.getAssociateProfileByID(associateID);
    }
    
    @GetMapping("/getAssociateBySkillName/{skillName}")
    public List<Profile> getAssociateBySkillName(@PathVariable String skillName){
        return service.getAssociateProfileBySkillName(skillName);
    }    
    
}
