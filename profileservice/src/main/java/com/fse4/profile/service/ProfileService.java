package com.fse4.profile.service;

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

import com.fse4.profile.domain.ProfileTO;
import com.fse4.profile.exception.APIGatewayURIException;
import com.fse4.profile.exception.InvalidMobileNumberException;
import com.fse4.profile.exception.ProfileFoundException;
import com.fse4.profile.exception.ProfileNotFoundException;
import com.fse4.profile.model.Profile;
import com.fse4.profile.repository.ProfileRepository;
import com.fse4.profile.repository.SkillRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProfileService {

	
    @Value("${company.name}")
    private String companyName;	
    
    @Value("${uri.profile.addEvent}")
    private String addProfileEventUri;	    
    
    @Value("${uri.profile.updateEvent}")
    private String updateProfileEventUri;	    
    
    @Value("${uri.profile.deleteEvent}")
    private String deleteProfileEventUri;    
	
    @Autowired
    ProfileRepository profileRepository;
    
    @Autowired
    SkillRepository skillRepository;    
    
    public ProfileTO createProfile(ProfileTO profileTO) {
    	
    	log.info("ProfileSErvice : createProfile ");    	
    	if (!profileTO.getMobile().toString().matches("\\d{10}"))
    		throw new InvalidMobileNumberException("Invalid mobile number");
    	String userId = companyName+profileTO.getAssociateId();
    	if(profileRepository.findByAssociateId(userId).isPresent())
    		throw new ProfileFoundException("ProfileTO Already Exists : "+userId);
    	Profile addProfile = new Profile();
    	ModelMapper mapper = new ModelMapper();
    	addProfile = mapper.map(profileTO, Profile.class);
    	addProfile.setAssociateId(userId);
    	addProfile.setCreatedAt(new Date());
    	addProfile.setLastupdated(new Date());  
		/*
		 * URI uri = null; RestTemplate restTemplate = null; try { uri = new
		 * URI(addProfileEventUri ); } catch (URISyntaxException e) {
		 * e.printStackTrace(); throw new
		 * APIGatewayURIException("createProfile: API Gateway URL Exception :"
		 * +addProfileEventUri); } restTemplate = new RestTemplate(); ProfileTO
		 * createdProfile = restTemplate.postForObject(uri, addProfile,
		 * ProfileTO.class);
		 */    
        String restMessage = callToRestService(HttpMethod.POST, addProfileEventUri, addProfile);
        log.info("LOG createProfile: published profile add message to skilltrackertopic topic : "+restMessage);    	
    	profileRepository.save(addProfile);
    	ProfileTO createdProfile = mapper.map(addProfile, ProfileTO.class);
    	return createdProfile;    	
    }
    
    public ProfileTO updateProfile(String associateId, ProfileTO profileTO) {
    	log.info("ProfileSErvice : updateProfile ");    	
    	String userId = companyName+associateId;    	
		Profile profileDB = profileRepository.findByAssociateId(companyName+associateId)
		.orElseThrow(() -> new ProfileNotFoundException("ProfileTO not found : "+userId));
		profileDB.setEmail(profileTO.getEmail());
		profileDB.setMobile(profileTO.getMobile());
		profileDB.setName(profileTO.getName());
		profileDB.setLastupdated(new Date());		
		/*
		 * URI uri = null; RestTemplate restTemplate = null; try { uri = new
		 * URI(updateProfileEventUri ); } catch (URISyntaxException e) {
		 * e.printStackTrace(); throw new
		 * APIGatewayURIException("updateProfile: API Gateway URL Exception :"
		 * +updateProfileEventUri); } restTemplate = new RestTemplate(); ProfileTO
		 * updateProfileTO = new ModelMapper().map(profileDB, ProfileTO.class); String
		 * restMessage = restTemplate.postForObject(uri, profileDB, String.class);
		 */
        log.info("LOG ENTRY before callToRestService : "+profileDB);
        String restMessage = callToRestService(HttpMethod.PUT, updateProfileEventUri, profileDB);
        log.info("LOG ENTRY restMessage : published profile update message to skilltrackertopic topic : "+restMessage);    			
		profileRepository.save(profileDB);   
		ProfileTO updateProfileTO = new ModelMapper().map(profileDB, ProfileTO.class);		
		return updateProfileTO;    	
    }
    
    public void deleteProfile(String associateId) {
    	String userId = companyName+associateId;    	
    	Profile profileDB = profileRepository.findByAssociateId(userId)
    			.orElseThrow(() -> new ProfileNotFoundException("Profile not found to delete : "+userId));
		/*
		 * URI uri = null; RestTemplate restTemplate = null; try { uri = new
		 * URI(deleteProfileEventUri ); } catch (URISyntaxException e) {
		 * e.printStackTrace(); throw new
		 * APIGatewayURIException("deleteProfile: API Gateway URL Exception :"
		 * +updateProfileEventUri); } restTemplate = new RestTemplate();
		 */
        log.info("LOG before callToRestService : "+profileDB);    	
        String restMessage = callToRestService(HttpMethod.DELETE, deleteProfileEventUri, profileDB);
        log.info("LOG ENTRY restMessage : published profile update message to skilltrackertopic topic : "+restMessage);    			        
        profileRepository.delete(profileDB);
        if(!skillRepository.findByAssociateId(associateId).isEmpty())
        	skillRepository.deleteAll(skillRepository.findByAssociateId(associateId));        
    }

    private String callToRestService(HttpMethod httpMethod, String url, Profile requestObject) {
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
