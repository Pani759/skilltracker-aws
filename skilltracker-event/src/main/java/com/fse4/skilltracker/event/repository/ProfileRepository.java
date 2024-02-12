package com.fse4.skilltracker.event.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.fse4.skilltracker.event.entity.ProfileDocument;
import com.fse4.skilltracker.event.entity.SkillDocument;

@Repository
public interface ProfileRepository extends MongoRepository<ProfileDocument, String> {

    @Query(value = "{ 'associateId' : ?0}")
    ProfileDocument findByAssociateId(String associateId);	
	//List<ProfileDocument> findProfilesBySkillDocument(SkillDocument skill);    


}




