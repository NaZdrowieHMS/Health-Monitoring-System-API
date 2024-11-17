package agh.edu.pl.healthmonitoringsystem.persistence;

import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.AiPredictionSummaryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PredictionSummaryRepository extends JpaRepository<AiPredictionSummaryEntity, Long> {

    List<AiPredictionSummaryEntity> findByPatientId(Long patientId, Pageable pageable);
}
