package com.fse4.skilltracker.event.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.fse4.skilltracker.event.entity.SkillDocument;

@Repository
public interface SkillsRepository extends MongoRepository<SkillDocument, Integer> {

    //@Query(value = "{ 'associateId' : ?0, Fields: {'skillName' : ?1} }")
    //@Query(value = "{ $or: [ { 'associateId' : ?0 }, { 'skillName' : ?0 } ] }")
    @Query(value = "{'associateId' : ?0, skillName :?1}")    
    SkillDocument findByAssociateIdAndSkillName(String associateId, String skillName);
    
    //@Query(value = "{ 'associateId' : ?0, Fields: {'skillCode' : ?1} }")
    @Query(value = "{'associateId' : ?0, skillCode :?1}")    
    SkillDocument findByAssociateIdAndSkillCode(String associateId, String skillCode);

	List<SkillDocument> findByAssociateId(String associateId);    

	//@Query(value = "{ 'userId' : ?0, 'questions.$questionID' : ?1 }") 
	//List<PracticeQuestion> findPracticeQuestionByUserIdAndQuestionsQuestionID(int userId, int questionID);
	
	
}




