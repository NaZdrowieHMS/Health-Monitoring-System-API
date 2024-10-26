package agh.edu.pl.healthmonitoringsystemapplication.persistence;

import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.table.HealthComment;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.projection.HealthCommentWithAuthorProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthRepository extends JpaRepository<HealthComment, Long> {

    @Override
    Optional<HealthComment> findById(Long id);

    @Query(value = "SELECT h.health_comment_id AS healthCommentId, h.patient_id AS patientId, h.content AS content, h.modified_date AS modifiedDate, " +
            "h.doctor_id AS doctorId, h.doctor_name AS doctorName, h.doctor_surname AS doctorSurname, h.doctor_email AS doctorEmail, " +
            "h.doctor_pesel AS doctorPesel, h.pwz AS pwz " +
            "FROM health_comment_with_autor_data_view h WHERE h.patient_id = :patientId",
            countQuery = "SELECT COUNT(*) FROM health_comment_with_autor_data_view h WHERE h.patient_id = :patientId",
            nativeQuery = true)
    Page<HealthCommentWithAuthorProjection> getHealthCommentsWithAutorByPatientId(@Param("patientId") Long patientId, PageRequest pageRequest);
}