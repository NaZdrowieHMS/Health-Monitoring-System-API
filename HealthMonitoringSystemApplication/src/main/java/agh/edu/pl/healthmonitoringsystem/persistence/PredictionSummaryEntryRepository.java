package agh.edu.pl.healthmonitoringsystem.persistence;

import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.AiPredictionSummaryEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PredictionSummaryEntryRepository extends JpaRepository<AiPredictionSummaryEntryEntity, Long> {

    List<AiPredictionSummaryEntryEntity> findByRequestId(Long requestId);
}
