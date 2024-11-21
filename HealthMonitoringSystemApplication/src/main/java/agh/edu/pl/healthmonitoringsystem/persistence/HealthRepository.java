package agh.edu.pl.healthmonitoringsystem.persistence;

import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.HealthCommentEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.CommentWithAuthorProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthRepository extends JpaRepository<HealthCommentEntity, Long> {

    @Query("""
            SELECT new agh.edu.pl.healthmonitoringsystem.persistence.model.projection.CommentWithAuthorProjection(\
            h.id, h.content, h.modifiedDate, d.id, d.name, d.surname) \
            FROM HealthCommentEntity h \
            JOIN UserEntity d ON h.doctorId = d.id \
            WHERE h.patientId = :patientId""")
    Page<CommentWithAuthorProjection> getHealthCommentsWithAuthorByPatientId(@Param("patientId") Long patientId, Pageable pageable);

    @Query("""
            SELECT new agh.edu.pl.healthmonitoringsystem.persistence.model.projection.CommentWithAuthorProjection(\
            h.id, h.content, h.modifiedDate, d.id, d.name, d.surname) \
            FROM HealthCommentEntity h \
            JOIN UserEntity d ON h.doctorId = d.id \
            WHERE h.id = :commentId""")
    Optional<CommentWithAuthorProjection> getHealthCommentWithAuthorByCommentId(@Param("commentId") Long commentId);

    @Query("""
            SELECT new agh.edu.pl.healthmonitoringsystem.persistence.model.projection.CommentWithAuthorProjection(\
            h.id, h.content, h.modifiedDate, d.id, d.name, d.surname) \
            FROM HealthCommentEntity h \
            JOIN UserEntity d ON h.doctorId = d.id \
            WHERE h.patientId = :patientId AND h.doctorId = :doctorId""")
    Page<CommentWithAuthorProjection> getPatientHealthCommentsAuthoredBySpecificDoctor(@Param("doctorId") Long doctorId, @Param("patientId") Long patientId, Pageable pageable);

    @Query("""
            SELECT new agh.edu.pl.healthmonitoringsystem.persistence.model.projection.CommentWithAuthorProjection(\
            h.id, h.content, h.modifiedDate, d.id, d.name, d.surname) \
            FROM HealthCommentEntity h \
            JOIN UserEntity d ON h.doctorId = d.id \
            WHERE h.patientId = :patientId AND h.doctorId <> :doctorId""")
    Page<CommentWithAuthorProjection> getPatientHealthCommentsAuthoredByOtherDoctors(@Param("doctorId") Long doctorId, @Param("patientId") Long patientId, Pageable pageable);
}