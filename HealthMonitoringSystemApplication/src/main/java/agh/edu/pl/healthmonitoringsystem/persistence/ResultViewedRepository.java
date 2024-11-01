package agh.edu.pl.healthmonitoringsystem.persistence;

import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultViewedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ResultViewedRepository extends JpaRepository<ResultViewedEntity, Long> {

    Optional<ResultViewedEntity> findByResultIdAndPatientIdAndDoctorId(Long resultId, Long patientId, Long doctorId);
}
