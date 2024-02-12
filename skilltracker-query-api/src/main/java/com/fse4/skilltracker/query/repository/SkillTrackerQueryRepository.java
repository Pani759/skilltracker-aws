package com.fse4.skilltracker.query.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.fse4.skilltracker.query.entity.ProfileDocument;
import com.fse4.skilltracker.query.entity.SkillsDocument;

@Repository
public interface SkillTrackerQueryRepository extends MongoRepository<ProfileDocument, String> {

   // @Autowired
   // MongoTemplate mt;

    public ProfileDocument findByAssociateId(String associateId);
    
    @Query("{id : ?0}")      //SQL Equivalent : SELECT * FROM BOOK WHERE ID=?
    List<SkillsDocument> findByProfileSkills(String id);        
    
//    @Query("{name : ?0}") // SQL Equivalent : SELECT * FROM ASSOCIATES where name = ?
    //@Query("{$or : [{'name': { $regex: ?0, $options:'i' }}, {'description': { $regex: ?0, $options:'i' }}]}")
    @Query("{'name': { $regex: ?0}}")
    List<ProfileDocument> getAssociatesByName(String name);

    //@Query(value = "{ 'skillName': { $elemMatch: {'skillName' :?0, $elemMatch: {'expertiseLevel' : {$gt : 10}} } }")
    //@Query(value = "{'profileSkills.$skillName' : ?0 }")    
    //@Query(value = "{ 'skillName': { $elemMatch: {'profileSkills.$skillName' :?0} } }")    
    public List<ProfileDocument> findByProfileSkillsSkillName(String skillName );
    
    public List<ProfileDocument> findByProfileSkillsSkillCode(String skillCode);    
    
/**
 * You can create the query in two ways.

Using Repository

findByCategoryListText(String category)
Using Query Method

@Query("{'categoryList.text': ?0}")
findByCategoryList(String category)    
 */


}




