package agh.edu.pl.healthmonitoringsystem.persistence;

import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.PatientEntity;
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

    @Query(value = """
            SELECT p FROM PatientEntity p
            WHERE p.id IN (SELECT dp.patientId FROM DoctorPatientEntity dp WHERE dp.doctorId = :doctorId)
            ORDER BY p.createdDate DESC
            """)
    Page<PatientEntity> findPatientsByDoctorId(@Param("doctorId") Long doctorId, Pageable pageable);


    @Query(value = """
            SELECT p FROM PatientEntity p
            WHERE p.id NOT IN (SELECT dp.patientId FROM DoctorPatientEntity dp WHERE dp.doctorId = :doctorId)
            ORDER BY p.createdDate DESC
            """)
    Page<PatientEntity> findUnassignedPatientsByDoctorId(@Param("doctorId") Long doctorId, Pageable pageable);
}
