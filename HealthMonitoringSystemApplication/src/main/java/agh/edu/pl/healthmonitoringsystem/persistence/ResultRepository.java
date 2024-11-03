package agh.edu.pl.healthmonitoringsystem.persistence;

import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.ResultWithAiSelectedAndViewedProjection;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.ResultWithPatientDataProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<ResultEntity, Long> {

    @Override
    Optional<ResultEntity> findById(Long id);

    @Query(value = "SELECT * FROM result r WHERE r.patient_id = :patientId",
            countQuery = "SELECT COUNT(*) FROM result r WHERE r.patient_id = :patientId",
            nativeQuery = true)
    Page<ResultEntity> getPatientResultsByPatientId(@Param("patientId") Long patientId, Pageable pageable);

    @Query(value = "SELECT r.id AS id, r.test_type AS testType, r.data, r.data_type as dataType, " +
            "ras.id IS NOT NULL AS aiSelected, rv.id IS NOT NULL AS viewed, r.created_date AS createdDate " +
            "FROM result r " +
            "LEFT JOIN result_ai_selected ras ON r.id = ras.result_id AND ras.patient_id = :patientId AND ras.doctor_id = :doctorId " +
            "LEFT JOIN result_viewed rv ON r.id = rv.result_id AND rv.patient_id = :patientId AND rv.doctor_id = :doctorId " +
            "WHERE r.patient_id = :patientId",
            nativeQuery = true)
    List<ResultWithAiSelectedAndViewedProjection> getDoctorPatientResultWithAiSelectedAndViewed(
            @Param("doctorId") Long doctorId,
            @Param("patientId") Long patientId);

    @Query(value = "SELECT r.id, r.patient_id AS patientId, r.name, r.surname, r.email, r.pesel, " +
            "r.test_type AS testType, r.data, r.data_type as dataType, r.created_date AS createdDate " +
            "FROM result_viewed rv " +
            "JOIN result_with_patient_data r ON rv.result_id = r.id " +
            "WHERE rv.doctor_id = :doctorId",
            nativeQuery = true)
    List<ResultWithPatientDataProjection> getDoctorUnviewedResults(@Param("doctorId") Long doctorId);
}
