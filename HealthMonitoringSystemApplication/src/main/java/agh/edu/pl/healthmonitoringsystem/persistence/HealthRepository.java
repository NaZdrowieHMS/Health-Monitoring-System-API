package agh.edu.pl.healthmonitoringsystem.persistence;

import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.HealthCommentEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.HealthCommentWithAuthorProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthRepository extends JpaRepository<HealthCommentEntity, Long> {

    @Override
    Optional<HealthCommentEntity> findById(Long id);

    @Query(value = "SELECT h.health_comment_id AS healthCommentId, h.patient_id AS patientId, h.content AS content, h.modified_date AS modifiedDate, " +
            "h.doctor_id AS doctorId, h.doctor_name AS doctorName, h.doctor_surname " +
            "FROM health_comment_with_autor_data_view h WHERE h.patient_id = :patientId",
            countQuery = "SELECT COUNT(*) FROM health_comment_with_autor_data_view h WHERE h.patient_id = :patientId",
            nativeQuery = true)
    Page<HealthCommentWithAuthorProjection> getHealthCommentsWithAutorByPatientId(@Param("patientId") Long patientId, PageRequest pageRequest);
}