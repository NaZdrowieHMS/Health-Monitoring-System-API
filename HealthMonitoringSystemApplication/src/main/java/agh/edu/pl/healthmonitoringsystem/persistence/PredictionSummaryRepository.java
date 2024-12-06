package agh.edu.pl.healthmonitoringsystem.persistence;

import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.PredictionSummaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictionSummaryRepository extends JpaRepository<PredictionSummaryEntity, Long> {

//    List<AiPredictionSummaryEntity> findByPatientId(Long patientId, Pageable pageable);
}
