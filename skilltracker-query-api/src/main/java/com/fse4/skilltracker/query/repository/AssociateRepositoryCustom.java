package com.fse4.skilltracker.query.repository;

import org.springframework.data.mongodb.repository.Query;

import com.fse4.skilltracker.query.entity.ProfileDocument;

import java.util.Optional;

public interface AssociateRepositoryCustom {

    @Query("{associateId : ?0}")      //SQL Equivalent : SELECT * FROM BOOK WHERE ID=?
    Optional<ProfileDocument> getAssociateById(String id);

}
