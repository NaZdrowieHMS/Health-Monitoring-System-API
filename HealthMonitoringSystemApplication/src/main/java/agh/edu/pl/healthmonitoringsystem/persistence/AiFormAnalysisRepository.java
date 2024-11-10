package agh.edu.pl.healthmonitoringsystem.persistence;


import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.AiFormAnalysisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AiFormAnalysisRepository extends JpaRepository<AiFormAnalysisEntity, Long> {

    Optional<AiFormAnalysisEntity> findByFormId(Long formId);
}

