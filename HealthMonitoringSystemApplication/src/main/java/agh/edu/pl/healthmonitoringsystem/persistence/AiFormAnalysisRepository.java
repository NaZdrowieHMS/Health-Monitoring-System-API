package agh.edu.pl.healthmonitoringsystem.persistence;

import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.AiFormAnalysisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AiFormAnalysisRepository extends JpaRepository<AiFormAnalysisEntity, Long> {

    @Query("""
    SELECT a FROM AiFormAnalysisEntity a WHERE a.formId = :formId AND a.doctorId = :doctorId 
    ORDER BY a.createdDate DESC
    LIMIT 1 """)
    Optional<AiFormAnalysisEntity> findTopByFormIdAndDoctorId(Long formId, Long doctorId);
}

