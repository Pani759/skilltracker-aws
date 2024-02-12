package com.fse4.skilltracker.query.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.fse4.skilltracker.query.entity.ProfileDocument;
import com.fse4.skilltracker.query.entity.SkillsDocument;
import com.fse4.skilltracker.query.exception.RecordNotFoundException;
import com.fse4.skilltracker.query.model.Profile;
import com.fse4.skilltracker.query.model.Skills;
import com.fse4.skilltracker.query.repository.SkillTrackerQueryRepository;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SkillTrackerQueryService {

	private static final Logger log = LoggerFactory.getLogger(SkillTrackerQueryService.class);	
	  @Bean
	  public ModelMapper modelMapper() {
	    return new ModelMapper();
	  }	
    @Autowired
    private SkillTrackerQueryRepository associateRepo;
    
	private List<Skills> staticTechSkill = Arrays.asList(
            new Skills("JAVA", "T001"), new Skills("JAVA8/11/17", "T002"), 
            new Skills("SPRINGMVC", "T003"),  new Skills("SPRING BOOT", "T004"),   
            new Skills("HIBERNATE", "T005"),  new Skills("ANGULAR", "T006"),   
            new Skills("REACT", "T007" ),   new Skills("RESTFUL", "T008" ), 
            new Skills("GIT", "T009" ), 	new Skills("DOCKER", "T010" ),
            new Skills("JENKINS", "T011" ),new Skills("AWS", "T012" ), new Skills("AZURE", "T013" )
        );    	
	private List<Skills> staticNonTechSkill = Arrays.asList(new Skills("SPOKEN", "N001"  ),
			new Skills("COMMUNICATION", "N002"),new Skills("APTITUDE", "N003"));        


    public List<Profile> getAllAssociates (){
        List<ProfileDocument> associateListDB =  associateRepo.findAll();
        List<Profile> allProfiles = new ArrayList<Profile>();
        for (ProfileDocument profileDocument : associateListDB) {
			allProfiles.add(populatePrifileTO(profileDocument));
		}        
        return allProfiles;
    }

    public Profile getAssociateProfileByID (final String associateId){

    	String userId = "CTS"+associateId;
    	log.info("LOG:ENTER : getAssociateProfileByID " +userId);    	
    	ProfileDocument profileDocument =  associateRepo.findById(userId).orElseThrow(() -> new RecordNotFoundException("Associate Profile not found with ID: "+associateId));
    	Profile profileEntity = modelMapper().map(profileDocument, Profile.class);    	
    	log.info("LOG:ENTER : getAssociateProfileByID " +profileDocument);    	
        //System.out.println("SkillTrackerQueryRepository - getAllAssociates - After calling Repo findAll");
    	if(profileDocument == null) {
            log.error("Associate Profilenot found with given name");
            throw new RecordNotFoundException("Associate Profile not found with ID: "+associateId);
    	}
        return populatePrifileTO(profileDocument);
    }
    
    public List<Profile> getAssociatesByName (final String associateName){

    	boolean associateNameFlag = true;
    	List<Profile> nameProfiles = new ArrayList<>();
    	List<ProfileDocument> profileDocumentList =  associateRepo.getAssociatesByName(associateName);    	
    	for (ProfileDocument profileDocument : profileDocumentList) {
			nameProfiles.add(populatePrifileTO(profileDocument));
		}
        //return populatePrifileTOSkillList(profileDocumentList, associateName, associateNameFlag);
        //return populatePrifileTOSkillList(profileDocumentList, associateName, associateNameFlag);
    	return nameProfiles;
    }      
    
    public List<Profile> getAssociateProfileBySkillName (final String skillName){
        //System.out.println("SkillTrackerQueryRepository - getAllAssociates - Before calling Repo findAll");
        //List<ProfileDocument> profileDocumentList =  associateRepo.findAssociateProfileBySkillName(skillName);
        //System.out.println("SkillTrackerQueryRepository - getAllAssociates - After calling Repo findAll");
    	List<ProfileDocument> profileDocumentList =  associateRepo.findByProfileSkillsSkillCode(skillName);
    			//findByProfileSkillsSkillName(skillName);    	

        return populatePrifileTOSkillList(profileDocumentList, skillName, false);
    }    
    
    public Profile addProfile ( Profile associateProfile){
        ProfileDocument profileFromDB = null;
        ProfileDocument profileDocument = null;
        // check if the Profile is already present in the DB or not
        String associateId = "CTS_" + associateProfile.getAssociateId();
        profileFromDB = associateRepo.findByAssociateId(associateId);

        if(profileFromDB==null){
            associateProfile.setAssociateId(associateId);
            profileDocument = populateProfileDocument(associateProfile);
            //System.out.println("before save associateProfile: "+associateProfile.toString());
        }else{
        	Skills newSkill = associateProfile.getAssociateSkill();
			/*
			 * if(associateProfileSkillExists(profileFromDB, newSkill)) throw new
			 * SkillAlreadyExistsException(newSkill.getSkillName()
			 * +" Skilll already added for this Associate :"
			 * +profileFromDB.getName()+"("+profileFromDB.getAssociateId()+")");
			 */
    		SkillsDocument newDocument = new SkillsDocument();
    		newDocument.setSkillCode(newSkill.getSkillCode());
    		newDocument.setSkillName(newSkill.getSkillName());
    		newDocument.setExpertiseLevel(newSkill.getRating());        		
    		profileFromDB.getProfileSkills().add(newDocument);
    		//profileDocument = populateProfileDocument(profileFromDB); 
    		profileDocument = profileFromDB;
        }
        log.info("before save ProfileDocument: "+profileDocument.toString());            
        profileFromDB = associateRepo.save(profileDocument);     
        return populatePrifileTO(profileFromDB);
    }
    
    public Profile updateProfile ( String associateId, Profile newSkillRating) {

    	String userId = "CTS_"+associateId;
    	ProfileDocument profileDocument = associateRepo.findByAssociateId(userId);
    	List<SkillsDocument> profileSkillDocument = associateRepo.findByProfileSkills(userId);
	    //check if the Profile is already present in the DB or not
		/*
		 * if(profileDocument ==null || !associateProfileSkillExists(profileDocument,
		 * newSkillRating.getAssociateSkill())){ throw new
		 * RecordNotFoundException("Record not found for associate id :"+userId); }
		 */
    	List<SkillsDocument> updateSkillRatings = new ArrayList<>();
    	if(isUpdateAllowed(profileDocument, newSkillRating.getAssociateSkill())) {
    		for (SkillsDocument skills : profileDocument.getProfileSkills()) {
    			if(skills.getSkillName().equalsIgnoreCase(newSkillRating.getAssociateSkill().getSkillName())) {
    				skills.setExpertiseLevel(newSkillRating.getAssociateSkill().getRating());
    			}
    			updateSkillRatings.add(skills);
    		}    	
        	profileDocument.setLastupdated(new Date());
        	profileDocument.setProfileSkills(updateSkillRatings);
        	associateRepo.save(profileDocument);        	
    	}   	

        //log.info("LOG ENTRY - Sending Profile Object to kafka server");
        System.out.println("Sending Profile Object to kafka server");
        //sendKafkaMessage("INSERT", profile);

    return populatePrifileTO(profileDocument);
}



	/**
	 * This method validates profile update 
	 *
	 */
	private boolean isUpdateAllowed (final ProfileDocument profileDocument, Skills newSkillRating){
	    //check if the profile was inserted 10 days ago
	    Date lastUpdated = profileDocument.getLastupdated();
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(lastUpdated);
	    LocalDate localDateFromDB = LocalDate.of(cal.get(Calendar.YEAR),
	            cal.get(Calendar.MONTH) + 1,
	            cal.get(Calendar.DAY_OF_MONTH));
	    Calendar currentDate = Calendar.getInstance();
	    currentDate.setTime(new Date());
	    LocalDate currentLocalDate = LocalDate.of(currentDate.get(Calendar.YEAR),
	    		currentDate.get(Calendar.MONTH) + 1,
	    		currentDate.get(Calendar.DAY_OF_MONTH));
	    Period age = Period.between(localDateFromDB, currentLocalDate );
	    
		/*
		 * if(age.getDays() > 3){ throw new
		 * ProfileUpdateNotAllowedException("Profile Record not allowed to update for associate id :"
		 * +profileDocument.getAssociateId()); }
		 */
	    return true;
	}


    private ProfileDocument populateProfileDocument(Profile profile){
    	
    	List<SkillsDocument> saveSkills = new ArrayList<>();
    	
    	if(!profile.getTechnicalSkills().isEmpty())
	    	for (Skills  skills  : profile.getTechnicalSkills()) {
	    		saveSkills.add(populateSkillsDocument(skills));
			}
    	if(!profile.getNonTechnicalSkills().isEmpty())    	
	    	for (Skills  skills  : profile.getNonTechnicalSkills()) {
	    		saveSkills.add(populateSkillsDocument(skills));
			}    	
    	SkillsDocument skillsDocument = new SkillsDocument();
    	if(profile.getAssociateSkill() !=null) {
        	skillsDocument.setSkillCode(profile.getAssociateSkill().getSkillCode());
        	skillsDocument.setSkillName(profile.getAssociateSkill().getSkillName());    	
        	skillsDocument.setExpertiseLevel(profile.getAssociateSkill().getRating());    	
        	saveSkills.add(skillsDocument);    		
    	}

    	ProfileDocument profileDocument = new ProfileDocument(profile.getAssociateId(), profile.getName(), 
    			profile.getMobile(), profile.getEmail(), new Date(), new Date(), saveSkills);
    	return profileDocument;
    }
    
    private Profile populatePrifileTO(ProfileDocument dbProfileDocument){
    	
    	List<Skills> techSkills = new ArrayList<>();
    	List<Skills> nonTechSkills = new ArrayList<>();    	
    	
    	for (SkillsDocument  skills  : dbProfileDocument.getProfileSkills()) {
    		if (staticNonTechSkill.contains(new Skills(skills.getSkillName(), skills.getSkillCode()))) {
    			nonTechSkills.add(populateSkillsTO(skills));				
			}else {
	    		techSkills.add(populateSkillsTO(skills));				
			}

		}
    	Profile profileTO = new Profile(dbProfileDocument.getAssociateId(), dbProfileDocument.getName(), dbProfileDocument.getMobile(), 
    			dbProfileDocument.getEmail(),null, techSkills, nonTechSkills)  ;  	
    	return profileTO;
    }    
    
    private List<Profile> populatePrifileTOSkillList(List<ProfileDocument> dbProfileSkillsList, String name, boolean associateFlag){
    	
    	List<Profile> profileSkillsList = new ArrayList<>();
    	ProfileDocument profileDocMatch = null;
    	for (ProfileDocument profileSkill : dbProfileSkillsList) {
    		if(StringUtils.isNotEmpty(name)) {
    			if(associateFlag) {
    				//Pattern.compile(Pattern.quote(profileSkill.getName()), Pattern.CASE_INSENSITIVE).matcher(name).find();    				
    				//if(name.contains(profileSkill.getName())) {
    				if(Pattern.compile(profileSkill.getName(), Pattern.CASE_INSENSITIVE).matcher("").reset(name).hitEnd()) {    				
    					profileDocMatch = profileSkill;
    				}
    			}else {
    				profileDocMatch = profileSkillByName(profileSkill, name);
        		}
    		} 
    		if(profileDocMatch !=null)
    			profileSkillsList.add(populatePrifileTO(profileDocMatch));
		}
    	if(profileSkillsList.isEmpty()) {
                log.error("Associate not found with given name");
                throw new RecordNotFoundException("Associate Profile skill not found: "+name);
    	}

    	return profileSkillsList;
    }        
    
    private ProfileDocument profileSkillByName(ProfileDocument profileSkill, String skillName) {
    	for (SkillsDocument  skills  : profileSkill.getProfileSkills()) {
    		if(skills.getSkillName().equalsIgnoreCase(skillName)
    				|| skills.getSkillCode().equalsIgnoreCase(skillName)) 
    			return profileSkill;
    	}
    	return null;
    }
    
    private SkillsDocument populateSkillsDocument(Skills skills){
    	
    	SkillsDocument skillsDocument = new SkillsDocument();
    	skillsDocument.setExpertiseLevel(skills.getRating());
    	skillsDocument.setSkillCode(skills.getSkillCode());
    	skillsDocument.setSkillName(skills.getSkillName());
    	
    	return skillsDocument;
    }    
    private Skills populateSkillsTO(SkillsDocument dbSkills){
    	
    	Skills skillsTO = new Skills();
    	skillsTO.setRating(dbSkills.getExpertiseLevel());
    	skillsTO.setSkillCode(dbSkills.getSkillCode());
    	skillsTO.setSkillName(dbSkills.getSkillName());
    	
    	return skillsTO;
    }   
    private boolean associateProfileSkillExists(ProfileDocument dbProfileDocument, Skills newSkill) {
    	
    	for (SkillsDocument  skills  : dbProfileDocument.getProfileSkills()) {
    		if(staticNonTechSkill.contains(new Skills(newSkill.getSkillName(), newSkill.getSkillCode()))
    				|| staticTechSkill.contains(new Skills(newSkill.getSkillName(), newSkill.getSkillCode())))
    			return true;
    	} 
    	return false;
    }

}
