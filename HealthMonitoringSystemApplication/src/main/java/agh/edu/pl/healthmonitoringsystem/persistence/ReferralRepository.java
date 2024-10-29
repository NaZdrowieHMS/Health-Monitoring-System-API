package agh.edu.pl.healthmonitoringsystem.persistence;

import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ReferralEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.PatientReferralWithCommentProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReferralRepository extends JpaRepository<ReferralEntity, Long> {

    @Override
    Optional<ReferralEntity> findById(Long id);

    @Query(value = "SELECT r.referral_id AS referralId, r.comment_id AS commentId, r.doctor_id AS doctorId, r.patient_id AS patientId, " +
            "r.test_type AS testType, r.referral_number AS referralNumber, r.completed AS completed, r.comment_content AS commentContent, " + // Dodana spacja tutaj
            "r.modified_date AS modifiedDate " +
            "FROM referral_with_comment_view r WHERE r.patient_id = :patientId",
            countQuery = "SELECT COUNT(*) FROM referral_with_comment_view r WHERE r.patient_id = :patientId",
            nativeQuery = true)
    Page<PatientReferralWithCommentProjection> getPatientReferralsByPatientId(@Param("patientId") Long patientId, Pageable pageable);
}