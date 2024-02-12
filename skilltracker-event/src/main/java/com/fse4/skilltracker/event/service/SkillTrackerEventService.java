/**
 * 
 */
package com.fse4.skilltracker.event.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.fse4.skilltracker.event.config.KafkaMessage;
import com.fse4.skilltracker.event.config.KafkaProducerProfile;
import com.fse4.skilltracker.event.entity.ProfileDocument;
import com.fse4.skilltracker.event.entity.SkillDocument;
import com.fse4.skilltracker.event.exception.SyncToReadDatabaseFailed;
import com.fse4.skilltracker.event.model.Profile;
import com.fse4.skilltracker.event.model.Skills;
import com.fse4.skilltracker.event.repository.ProfileRepository;
import com.fse4.skilltracker.event.repository.SkillsRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cogjava759
 *
 */
@Slf4j
@Service
public class SkillTrackerEventService {

	@Autowired
	SequenceGeneratorService sequenceGeneratorService;
	
    @Autowired
    private ProfileRepository profileRepository;		
    
    @Autowired
    private SkillsRepository skillRepository;		    
	  
    @Autowired
    private KafkaProducerProfile kafkaProfucer;
    
    public void publishProfileAddEvent ( Profile associateProfile){

    	ProfileDocument profileDocument = profileRepository.findByAssociateId(associateProfile.getAssociateId());
    	if(profileDocument == null)
    		publishKafkaEventMessage(associateProfile, null, "ADDPROFILE");
    }
    
    public void publishProfileUpdateEvent ( Profile associateProfile){

    	ProfileDocument profileDocument = profileRepository.findByAssociateId(associateProfile.getAssociateId());
    	if(profileDocument != null)    	
    		publishKafkaEventMessage(associateProfile, null, "UPDATEPROFILE");
    }    
    
    public void publishProfileDeleteEvent ( Profile associateProfile){

    	ProfileDocument profileDocument = profileRepository.findByAssociateId(associateProfile.getAssociateId());
    	if(profileDocument != null)    	
    		publishKafkaEventMessage(associateProfile, null,  "DELETEPROFILE");
    }      
    
    public void publishSkillAddEvent ( Skills skill){

    	if(skillRepository.findByAssociateIdAndSkillName(skill.getAssociateId(), skill.getSkillName()) ==null
    			|| skillRepository.findByAssociateIdAndSkillCode(skill.getAssociateId(), skill.getSkillCode()) == null) {
    		publishKafkaEventMessage( null, skill, "ADDSKILL");            		
    	}

    }
    
    public void publishSkillUpdateEvent(Skills skill){

    	if(skillRepository.findByAssociateIdAndSkillName(skill.getAssociateId(), skill.getSkillName()) !=null
    			|| skillRepository.findByAssociateIdAndSkillCode(skill.getAssociateId(), skill.getSkillCode()) != null) {
    		publishKafkaEventMessage( null, skill, "UPDATESKILL");            		
    	}
    }   
    
    public void  publishSkillDeleteEvent(Skills skill){

    	if(skillRepository.findByAssociateIdAndSkillName(skill.getAssociateId(), skill.getSkillName()) !=null
    			|| skillRepository.findByAssociateIdAndSkillCode(skill.getAssociateId(), skill.getSkillCode()) != null) {
    		publishKafkaEventMessage( null, skill, "DELETESKILL");            		
    	}
    }
    
    /**
    <p>This method saves the Profile Object received from the Event part of CQRS Pattern and saves into the
    * Mongo DB
    * </p>
    * @param kafkaMessage
    */
   public void syncProfileDetailsToQueryDatabse(final KafkaMessage kafkaMessage){

	   System.out.println("########## - syncProfileQueryInformation to MongoDB");
       ModelMapper mapper = new ModelMapper();
       try {
           ProfileDocument profileDocument = mapper.map(kafkaMessage.getProfile(), ProfileDocument.class);	   
           if(kafkaMessage.getMongoOpsCode().equals("ADDPROFILE")) {
               profileRepository.save(profileDocument);    	   
               log.info("Associate Profile added in MongoDB");           
           }
           if(kafkaMessage.getMongoOpsCode().equals("UPDATEPROFILE") ) {
        	   List<SkillDocument> skills;
        	   if(profileRepository.findByAssociateId(profileDocument.getAssociateId()) != null) {
        		   skills = profileRepository.findByAssociateId(profileDocument.getAssociateId()).getProfileSkills();
                   profileDocument.setProfileSkills(skills);    		   
        	   }
               profileRepository.save(profileDocument);    	   
               log.info("Associate Profile updated in MongoDB");           
           }
           if(kafkaMessage.getMongoOpsCode().equals("DELETEPROFILE")) {
        	   String associateId = profileDocument.getAssociateId();
               profileRepository.deleteById(associateId);
               skillRepository.deleteAll(skillRepository.findByAssociateId(associateId));
               log.info("Associate Profile deleted from MongoDB");               
           }       		
		} catch (Exception e) {
			throw new SyncToReadDatabaseFailed("Skill Details not sync with Mongo : "+e.getMessage());
		}

   }
   
   public void syncSkillDetailsToQueryDatabase(final KafkaMessage kafkaMessage){   

       ModelMapper mapper = new ModelMapper();
       try {
           Skills skillTO = kafkaMessage.getSkills();
           SkillDocument skillDocument = mapper.map(skillTO, SkillDocument.class);
    	   log.info("########## - syncSkillDetailsToQueryDatabase to MongoDB : "+skillDocument);       
           if(kafkaMessage.getMongoOpsCode().equals("ADDSKILL")) {
               ProfileDocument profileDB = profileRepository.findByAssociateId(skillDocument.getAssociateId());
               if(profileDB.getProfileSkills().isEmpty() || profileDB.getProfileSkills() != null)
            	   profileDB.getProfileSkills().add(skillDocument);
               profileRepository.save(profileDB);
               skillDocument.setId(sequenceGeneratorService.generateSequence(SkillDocument.SEQUENCE_NAME));
               //skillRepository.findById(0);
               skillRepository.save(skillDocument);
               log.info("Associate Skill Added MongoDB : "+skillDocument);               
           }
           if(kafkaMessage.getMongoOpsCode().equals("UPDATESKILL")) {
               ProfileDocument profileDB = profileRepository.findByAssociateId(skillDocument.getAssociateId());
               List<SkillDocument> dbSkills = profileDB.getProfileSkills();
               List<SkillDocument> updatedList = findOneAndUpdate(dbSkills, skillDocument);
               profileDB.setProfileSkills(updatedList);
               profileRepository.save(profileDB);
               //skillRepository.delete(skillDocument);       
               SkillDocument dbSkill = skillRepository.findByAssociateIdAndSkillCode(skillDocument.getAssociateId(), skillDocument.getSkillCode());
               skillDocument.setId(dbSkill.getId());
               skillRepository.save(skillDocument);           
               log.info("Associate Skill updated MongoDB : "+skillDocument);               
           }       
           if(kafkaMessage.getMongoOpsCode().equals("DELETESKILL")) {
               log.info("before delete SkillDocument: "+skillDocument.toString());  
               ProfileDocument profileDB = profileRepository.findByAssociateId(skillDocument.getAssociateId());
               List<SkillDocument> dbSkills = profileDB.getProfileSkills();
               List<SkillDocument> updatedList = findOneAndRemove(dbSkills, skillDocument);
               profileDB.setProfileSkills(updatedList);
               profileRepository.save(profileDB);
               SkillDocument dbSkill = skillRepository.findByAssociateIdAndSkillCode(skillDocument.getAssociateId(), skillDocument.getSkillCode());
               skillDocument.setId(dbSkill.getId());               
               skillRepository.delete(skillDocument);
               log.info("Associate Skill deleted from MongoDB");               
           }
           if(kafkaMessage.getMongoOpsCode().equals("DELETESKILL")) {
               skillRepository.delete(skillDocument);
               log.info("Associate Skill deleted from MongoDB");               
           }     		
		} catch (Exception e) {
			throw new HttpClientErrorException(HttpStatus.EXPECTATION_FAILED, "Skill Details not sync with Mongo : "+e.getMessage());
		}       

     
   }    
   private void publishKafkaEventMessage(Profile profile, Skills skill, String message) {
           log.info("LOG ENTRY - publish message to skilltrackertopic topic: "+message);
           System.out.println("Sending Profile Object to kafka server");
           KafkaMessage kafkaMsgEent = new KafkaMessage(message, profile, skill);
           kafkaProfucer.sendMessage(kafkaMsgEent);                
	   
   }
   
   private List<SkillDocument> findOneAndUpdate(List<SkillDocument> docList, SkillDocument newSkill){
	   List<SkillDocument> updatedList =
			   docList.stream().map(item->(item.getSkillName().equals(newSkill.getSkillName())
					   						|| item.getSkillCode().equals(newSkill.getSkillCode())) ? newSkill : item)
			   .collect(Collectors.toList());    	   
	   return updatedList;
   }   
   
   private List<SkillDocument> findOneAndRemove(List<SkillDocument> docList, SkillDocument newSkill){
	   List<SkillDocument> updatedList =
			   docList.stream().map(item->(item.getSkillName().equals(newSkill.getSkillName())
					   						|| item.getSkillCode().equals(newSkill.getSkillCode())) ? null : item)
			   .collect(Collectors.toList());    	   
	   return updatedList;
   }      
    
}
