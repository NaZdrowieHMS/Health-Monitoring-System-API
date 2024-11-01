package agh.edu.pl.healthmonitoringsystem.persistence;

import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultAiSelectedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultAiSelectedRepository extends JpaRepository<ResultAiSelectedEntity, Long> {

    Optional<ResultAiSelectedEntity> findByResultIdAndPatientIdAndDoctorId(Long resultId, Long patientId, Long doctorId);
}
