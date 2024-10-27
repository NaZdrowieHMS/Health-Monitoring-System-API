package agh.edu.pl.healthmonitoringsystemapplication.persistence;

import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.entity.PatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

    @Override
    Optional<PatientEntity> findById(Long id);

    @Query(value = "SELECT id, name, surname, email, pesel, created_date, modified_date FROM doctor_patients_view p WHERE p.doctor_id = :doctorId",
            countQuery = "SELECT COUNT(*) FROM doctor_patients_view p WHERE p.doctor_id = :doctorId",
            nativeQuery = true)
    Page<PatientEntity> findPatientsByDoctorId(@Param("doctorId") Long doctorId, Pageable pageable);
}
