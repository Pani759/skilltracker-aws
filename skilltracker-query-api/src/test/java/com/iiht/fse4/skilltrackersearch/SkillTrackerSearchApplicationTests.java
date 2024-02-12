package com.iiht.fse4.skilltrackersearch;

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

import com.fse4.skilltracker.query.entity.ProfileDocument;
import com.fse4.skilltracker.query.entity.SkillsDocument;
import com.fse4.skilltracker.query.model.Profile;
import com.fse4.skilltracker.query.repository.SkillTrackerQueryRepository;
import com.fse4.skilltracker.query.service.SkillTrackerQueryService;

@ExtendWith(MockitoExtension.class)
class SkillTrackerSearchApplicationTests {

	@Mock
	private SkillTrackerQueryRepository mockProfileRepo;

	// @InjectMocks allows to create profileService and inject the mocked
	// mockProfileRepo into it
	@InjectMocks
	SkillTrackerQueryService profileService;

	// Mockito.mock() method to achieve the same objective:
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);

	}

	/*
	 * @Test void getAssociateByID() {
	 * 
	 * SkillsDocument skills = new SkillsDocument();
	 * skills.setExpertiseLevel(Double.valueOf("11")); skills.setSkillCode("T001");
	 * skills.setSkillName("JAVA");
	 *//**
		 * 11 T001 JAVA CTS_12345 11 T002 JAVA8/11/17 CTS_12345 11 T003 SPRINGMVC
		 * CTS_12345 11 T004 SPRINGBOOT CTS_12345 11 T005 HIBERNATE CTS_12345 11 T006
		 * ANGULAR CTS_12345 11 T007 REACT CTS_12345 11 T008 RESTFUL CTS_12345 11 T009
		 * GIT CTS_12345 11 T010 DOCKER CTS_12345 11 T011 JENKINS CTS_12345
		 * 
		 **//*
			 * 
			 * List<SkillsDocument> skillsList = new java.util.ArrayList();
			 * skillsList.add(skills); // Given //CTS_12345 2023-10-30 19:05:48.886000
			 * test1name@gmail.com 2023-10-30 19:05:48.886000 8688877774 test1name
			 * ProfileDocument profile = new ProfileDocument("CTS12345",
			 * "test1name",Long.valueOf(868887774), "test1name@gmail.com", null, null,
			 * skillsList);
			 * 
			 * // When calling findById returns Optional
			 * when(mockProfileRepo.findByAssociateId("CTS_12345")).thenReturn(profile);
			 * //Profile associateProfile =
			 * this.profileService.getAssociateProfileByID("CTS12345");
			 * 
			 * // Then //assertTrue(returnedStudent.isPresent());
			 * //verify(this.mockProfileRepo).findByAssociateId("CTS12345") ; }
			 */

	@Test
	void getAllAssociateProfiles() {

		SkillsDocument skills = new SkillsDocument();
		skills.setExpertiseLevel(Double.valueOf("11"));
		skills.setSkillCode("T001");
		skills.setSkillName("JAVA");
		/**
		 * 11 T001 JAVA CTS_12345 11 T002 JAVA8/11/17 CTS_12345 11 T003 SPRINGMVC
		 * CTS_12345 11 T004 SPRINGBOOT CTS_12345 11 T005 HIBERNATE CTS_12345 11 T006
		 * ANGULAR CTS_12345 11 T007 REACT CTS_12345 11 T008 RESTFUL CTS_12345 11 T009
		 * GIT CTS_12345 11 T010 DOCKER CTS_12345 11 T011 JENKINS CTS_12345
		 * 
		 **/

		List<SkillsDocument> skillsList = new java.util.ArrayList();
		skillsList.add(skills);
		// Given
		// CTS_12345 2023-10-30 19:05:48.886000 test1name@gmail.com 2023-10-30
		// 19:05:48.886000 8688877774 test1name
		ProfileDocument profile = new ProfileDocument("CTS12345", "test1name", Long.valueOf(868887774),
				"test1name@gmail.com", null, null, skillsList);

		// When calling the mocked repository method
		when(mockProfileRepo.findAll()).thenReturn(Arrays.asList(profile));
		List profileList = this.profileService.getAllAssociates();

		// Then
		// assertEquals(Arrays.asList(profile), profileList);
		verify(this.mockProfileRepo).findAll();
	}
}
