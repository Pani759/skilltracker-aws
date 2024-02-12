package com.fse4.skill.service;

import java.net.URI;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fse4.skill.domain.SkillTO;
import com.fse4.skill.exception.APIGatewayURIException;
import com.fse4.skill.exception.ProfileNotFoundException;
import com.fse4.skill.exception.SkillFoundException;
import com.fse4.skill.exception.SkillNotFoundException;
import com.fse4.skill.exception.SyncDataFailedException;
import com.fse4.skill.model.Profile;
import com.fse4.skill.model.Skill;
import com.fse4.skill.repository.ProfileRepository;
import com.fse4.skill.repository.SkillRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SkillService {

	@Value("${company.name}")
	private String companyName;

	@Value("${uri.skill.addEvent}")
	private String addSkillEventUri;

	@Value("${uri.skill.updateEvent}")
	private String updateSkillEventUri;

	@Value("${uri.skill.deleteEvent}")
	private String deleteSkillEventUri;

	@Autowired
	ProfileRepository profileRepository;
	@Autowired
	SkillRepository skillRepository;

	public SkillTO addSkill(String associateId, SkillTO skillTO) {

        log.info("Skill service addSkill : ");
    	String userId = companyName+skillTO.getAssociateId();
    	Profile associateProfile= profileRepository.findByAssociateId(userId)
    			.orElseThrow(() -> new ProfileNotFoundException("No Profile found to add skill for this associate: " +userId));
    	if(skillRepository.findByAssociateIdAndSkillName(userId,skillTO.getSkillName()).isPresent()) 
    		throw new SkillFoundException("Skill already added for this associate: "+ userId); 
    	if(skillRepository.findByAssociateIdAndSkillCode(userId, skillTO.getSkillCode()).isPresent()) 
    		throw new SkillFoundException("Skill already added for this associate: "+ userId);    	
    	Skill addSkill = new Skill();
    	ModelMapper mapper = new ModelMapper();
    	addSkill = mapper.map(skillTO, Skill.class);
    	addSkill.setAssociateId(userId);
    	addSkill.setCreatedAt(new Date());
    	addSkill.setLastupdated(new Date());  
        log.info("LOG before callToRestService skill add message : "+addSkill);
        try {
            String restMessage = callToRestService(HttpMethod.POST, addSkillEventUri, addSkill);        
            log.info("LOG  callToRestService successful: published profile add message to skilltrackertopic topic : "+restMessage);    				
		} catch (Exception e) {
			throw new SyncDataFailedException("Sync to Mongo failed : "+e.getMessage());
		}

    	skillRepository.save(addSkill);
    	SkillTO createdSkill= mapper.map(addSkill, SkillTO.class);		
		return createdSkill;
	}

	public SkillTO updateSkill(String associateId, SkillTO updateSkill) {

        log.info("Skill service updateSkill : ");
		Skill skillDB = null;
		String userId = companyName + associateId;
		ModelMapper mapper = new ModelMapper();

		Profile profile = profileRepository.findByAssociateId(userId).orElseThrow(
				() -> new ProfileNotFoundException("No Profile found to update skill for this associate: " + userId));

		skillDB = skillRepository.findByAssociateIdAndSkillName(userId, updateSkill.getSkillName())
				.orElseThrow(() -> new SkillNotFoundException("Skill not found for this associate: " + userId));
		if (skillDB == null)
			skillDB = skillRepository.findByAssociateIdAndSkillCode(userId, updateSkill.getSkillCode())
					.orElseThrow(() -> new SkillNotFoundException("Skill not found for this associate: " + userId));

		skillDB.setLastupdated(new Date());
		skillDB.setExpertiseLevel(updateSkill.getExpertiseLevel());
        log.info("LOG before callToRestService skill update message : "+skillDB);
        String restMessage = callToRestService(HttpMethod.PUT, updateSkillEventUri, skillDB);        		
		log.info("LOG callToRestService successfull: published skill update message to skilltrackertopic topic : "+restMessage);
		skillDB = skillRepository.save(skillDB);
		SkillTO updatedSkill = mapper.map(skillDB, SkillTO.class);
		return updatedSkill;
	}

	public void deleteSkills(String associateId, SkillTO skillTO) {

        log.info("Skill service deleteSkills : ");
		Skill dbSkill = null;
		String userId = companyName + associateId;
		Profile associateProfile = profileRepository.findByAssociateId(userId).orElseThrow(
				() -> new ProfileNotFoundException("No profile found to delete for this associate: " + userId));

		dbSkill = skillRepository.findByAssociateIdAndSkillName(userId, skillTO.getSkillName()).orElseThrow(
				() -> new SkillNotFoundException("Skill not found to delete for this associate: " + userId));
		if (dbSkill == null)
			dbSkill = skillRepository.findByAssociateIdAndSkillCode(userId, skillTO.getSkillCode())
					.orElseThrow(() -> new SkillNotFoundException("Skill not found to delete for this associate: " + userId));
        log.info("LOG before callToRestService skill delete message : "+skillTO);
        String restMessage = callToRestService(HttpMethod.DELETE, deleteSkillEventUri, dbSkill);        		
		log.info("LOG callToRestService successfull: published skill update message to skilltrackertopic topic : "+restMessage);		
		skillRepository.delete(dbSkill);

	}

    private String callToRestService(HttpMethod httpMethod, String url, Skill requestObject) {
        log.info("LOG ENTRY callToRestService : "+url);
        try {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Object> entity = new HttpEntity<>(requestObject, requestHeaders);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, httpMethod, entity, String.class);
            if (responseEntity.getStatusCodeValue() == 200 && responseEntity.getBody() != null) {
                return responseEntity.getBody();
            }

        } catch (HttpClientErrorException exception) {
        	exception.printStackTrace();
			throw new APIGatewayURIException("createProfile: API Gateway URL Exception :"+url+exception.getLocalizedMessage());        	
        }
        catch (HttpStatusCodeException exception) {
        	exception.printStackTrace();
			throw new APIGatewayURIException("createProfile: API Gateway URL Exception :"+url+exception.getLocalizedMessage());
        }
        log.info("LOG EXIT : callToRestService message posted to skilltrackertopic topic : ");        
        return null;
    }

}
