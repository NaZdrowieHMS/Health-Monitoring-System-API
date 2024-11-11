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
    @Query("""
            SELECT new agh.edu.pl.healthmonitoringsystem.persistence.model.projection.PatientReferralWithCommentProjection(\
            r.id, r.patientId, r.testType, r.number, r.completed, d.id, d.name, d.surname, r.comment, \
            r.modifiedDate, r.createdDate) \
            FROM ReferralEntity r JOIN DoctorEntity d ON r.doctorId = d.id \
            WHERE r.patientId = :patientId \
            ORDER BY r.completed ASC, r.createdDate DESC""")
    Page<PatientReferralWithCommentProjection> getPatientReferralsByPatientId(@Param("patientId") Long patientId, Pageable pageable);

    @Query("""
            SELECT new agh.edu.pl.healthmonitoringsystem.persistence.model.projection.PatientReferralWithCommentProjection(\
            r.id, r.patientId, r.testType, r.number, r.completed, d.id, d.name, d.surname, r.comment, \
            r.modifiedDate, r.createdDate) \
            FROM ReferralEntity r JOIN DoctorEntity d ON r.doctorId = d.id WHERE r.id = :referralId""")
    Optional<PatientReferralWithCommentProjection> getPatientReferralWithAllData(@Param("referralId") Long referralId);
}
