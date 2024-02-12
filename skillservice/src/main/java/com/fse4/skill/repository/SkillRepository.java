package com.fse4.skill.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fse4.skill.model.Skill;


@Repository
public interface SkillRepository extends JpaRepository<Skill, String> {

	Optional<List<Skill>> findByAssociateId(String associateId);
	Optional<Skill> findByAssociateIdAndSkillName(String associateId, String skillName);
	Optional<Skill> findByAssociateIdAndSkillCode(String associateId, String skillCode);	

}
