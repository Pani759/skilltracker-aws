package com.fse4.profile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fse4.profile.model.Profile;


@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {

	Optional<Profile> findByAssociateId(String associateId);

}
