package agh.edu.pl.healthmonitoringsystem.persistence;

import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.DoctorPatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorPatientRepository extends JpaRepository<DoctorPatientEntity, Long> {

    Optional<DoctorPatientEntity> findByPatientIdAndDoctorId(Long patientId, Long doctorId);
}