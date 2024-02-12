// Java Program to Illustrate Unit Testing of Service Class

package com.fse4.skilltracker.addprofile.service;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fse4.skilltracker.event.entity.ProfileDocument;
import com.fse4.skilltracker.event.entity.SkillDocument;
import com.fse4.skilltracker.event.model.Profile;
import com.fse4.skilltracker.event.repository.ProfileRepository;
import com.fse4.skilltracker.event.service.SkillTrackerEventService;


/**
 * @ExtendWith(MockitoExtension.class) enables Mockito support
 *
 */
@ExtendWith(MockitoExtension.class)
//@RunWith(MockitoJUnitRunner.class)
class SkillTrackerEventServiceTest {

	
	@Mock private ProfileRepository mockProfileRepo;
	
	//	@InjectMocks allows to create profileService and inject the mocked mockProfileRepo into it
	@InjectMocks SkillTrackerEventService profileService;	
	

	//Mockito.mock() method to achieve the same objective:
	@BeforeEach void setUp()
	{
        MockitoAnnotations.initMocks(this);

	}

	
	@Test void getAssociateByID()
	{
		
		SkillDocument skills = new SkillDocument();
		//skills.setExpertiseLevel(Integer.valueOf("11"));
		//skills.setSkillId("T001");
		skills.setSkillName("JAVA");
		/**
		 * 11	T001	JAVA	CTS_12345
11	T002	JAVA8/11/17	CTS_12345
11	T003	SPRINGMVC	CTS_12345
11	T004	SPRINGBOOT	CTS_12345
11	T005	HIBERNATE	CTS_12345
11	T006	ANGULAR	CTS_12345
11	T007	REACT	CTS_12345
11	T008	RESTFUL	CTS_12345
11	T009	GIT	CTS_12345
11	T010	DOCKER	CTS_12345
11	T011	JENKINS	CTS_12345
		 * 
		 **/
		
		List<SkillDocument> skillsList = new java.util.ArrayList();
		skillsList.add(skills);
        // Given
		//CTS_12345	2023-10-30 19:05:48.886000	test1name@gmail.com	2023-10-30 19:05:48.886000	8688877774	test1name
        //ProfileDocument profile = new ProfileDocument("CTS_12345", "test1name",Long.valueOf("8688877776"), "test1name@gmail.com", null, null);

        // When calling findById returns Optional
        //when(mockProfileRepo.findByAssociateId("CTS_12345")).thenReturn(profile);
       // Profile associateProfile = this.profileService.getAssociateByID("CTS_12345");

        // Then
        //assertTrue(returnedStudent.isPresent());
        //verify(this.mockProfileRepo).findByAssociateId("CTS_12345")		;
	}
	
	@Test void getAllAssociateProfiles()
	{
		
		SkillDocument skills = new SkillDocument();
		//skills.setExpertiseLevel(Integer.valueOf("11"));
		//skills.setSkillId("T001");
		skills.setSkillName("JAVA");
		/**
		 * 11	T001	JAVA	CTS_12345
11	T002	JAVA8/11/17	CTS_12345
11	T003	SPRINGMVC	CTS_12345
11	T004	SPRINGBOOT	CTS_12345
11	T005	HIBERNATE	CTS_12345
11	T006	ANGULAR	CTS_12345
11	T007	REACT	CTS_12345
11	T008	RESTFUL	CTS_12345
11	T009	GIT	CTS_12345
11	T010	DOCKER	CTS_12345
11	T011	JENKINS	CTS_12345
		 * 
		 **/
		
		List<SkillDocument> skillsList = new java.util.ArrayList();
		skillsList.add(skills);
        // Given
		//CTS_12345	2023-10-30 19:05:48.886000	test1name@gmail.com	2023-10-30 19:05:48.886000	8688877774	test1name
        //ProfileDocument profile = new ProfileDocument("CTS_12345", "test1name",Long.valueOf("8688877776"), "test1name@gmail.com", null, null);

        // When calling the mocked repository method
       // when(mockProfileRepo.findAll()).thenReturn(Arrays.asList(profile));
        //List profileList = this.profileService.getAllAssociates();
        
        // Then
        //assertEquals(Arrays.asList(profile), profileList);
        //verify(this.mockProfileRepo).findAll();
	}	
}
