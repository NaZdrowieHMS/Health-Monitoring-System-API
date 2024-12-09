package agh.edu.pl.healthmonitoringsystem.persistence;

import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.PredictionSummaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface PredictionSummaryRepository extends JpaRepository<PredictionSummaryEntity, Long> {
    List<PredictionSummaryEntity> findByDoctorIdAndPatientId(Long doctorId, Long patientId, Pageable pageable);

    List<PredictionSummaryEntity> findByDoctorId(Long doctorId, Pageable pageable);
}
