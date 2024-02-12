package com.fse4.skill.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fse4.skill.domain.SkillTO;
import com.fse4.skill.model.Skill;
import com.fse4.skill.service.SkillService;


@RestController
@RequestMapping("/skill/api/v1/engineer")
public class SkillController {

    @Autowired
    SkillService skillService;

    @PostMapping("/add-skill/{associateId}")
    public SkillTO createSkill(@Valid @PathVariable(value = "associateId") String associateId, @RequestBody SkillTO addSkill) {
        return skillService.addSkill(associateId, addSkill);
    }

    @PutMapping("/update-skill/{associateId}")
    public SkillTO updateSkills(@PathVariable(value = "associateId") String associateId,
    		 @Valid @RequestBody SkillTO updateSkill) {
        return skillService.updateSkill(associateId, updateSkill);
    }
    
    @DeleteMapping("/delete-skill/{associateId}")
    public ResponseEntity<String> deleteSkill(@PathVariable(value = "associateId") String associateId,
    		@Valid @RequestBody SkillTO deleteSkill) {
        skillService.deleteSkills(associateId, deleteSkill);

        return ResponseEntity.ok("Skill deleted :"+deleteSkill.getSkillName()+" for this associate CTS"+associateId);
    }    
}
