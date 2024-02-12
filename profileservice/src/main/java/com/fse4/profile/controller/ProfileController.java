package com.fse4.profile.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fse4.profile.domain.ProfileTO;
import com.fse4.profile.service.ProfileService;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600 , allowCredentials = "false")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
@RestController
@RequestMapping("/skill-tracker/api/v1/engineer")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @PostMapping("/add-profile")
    public ProfileTO createProfile(@Valid @RequestBody ProfileTO profile) {
        return profileService.createProfile(profile);
    }

    @PutMapping("/update-profile/{associateId}")
    public ProfileTO updateProfile(@PathVariable(value = "associateId") String associateId,
                                           @Valid @RequestBody ProfileTO profileTO) {
        return profileService.updateProfile(associateId, profileTO);
    }

    @DeleteMapping("/delete-profile/{associateId}")
    public ResponseEntity<String> deleteProfile(@PathVariable(value = "associateId") String associateId) {
        profileService.deleteProfile(associateId);

        return ResponseEntity.ok("Profile deleted: CTS"+associateId);
    }
}
