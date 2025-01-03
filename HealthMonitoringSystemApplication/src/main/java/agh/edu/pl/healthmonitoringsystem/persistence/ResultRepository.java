package agh.edu.pl.healthmonitoringsystem.persistence;

import agh.edu.pl.healthmonitoringsystem.domain.model.response.ResultOverview;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.ResultWithAiSelectedAndViewedProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<ResultEntity, Long> {

    Page<ResultEntity> getResultEntitiesByPatientId(@Param("patientId") Long patientId, Pageable pageable);

    @Query("""
        SELECT new agh.edu.pl.healthmonitoringsystem.domain.model.response.ResultOverview(
        r.id, r.patientId, r.testType, r.createdDate,
        CASE WHEN ras.id IS NOT NULL THEN true ELSE false END)
        FROM ResultEntity r
        LEFT JOIN ResultAiSelectedEntity ras ON r.id = ras.resultId AND ras.patientId = :patientId AND ras.doctorId = :doctorId
        WHERE r.patientId = :patientId
        ORDER BY r.createdDate DESC
        """)
    Page<ResultOverview> getResultsByPatientIdWithAiSelectedAndViewed(@Param("doctorId") Long doctorId, @Param("patientId") Long patientId, Pageable pageable);

    @Query("""
        SELECT new agh.edu.pl.healthmonitoringsystem.persistence.model.projection.ResultWithAiSelectedAndViewedProjection(
        r.id, r.patientId, r.testType, r.dataType, r.data, r.createdDate,
        CASE WHEN ras.id IS NOT NULL THEN true ELSE false END,
        CASE WHEN rv.id IS NOT NULL THEN true ELSE false END)
        FROM ResultEntity r
        LEFT JOIN ResultAiSelectedEntity ras ON r.id = ras.resultId
        LEFT JOIN ResultViewedEntity rv ON r.id = rv.resultId
        WHERE r.id = :resultId
        """)
    Optional<ResultWithAiSelectedAndViewedProjection> getResultByIdWithAiSelectedAndViewed(@Param("resultId") Long resultId);

    @Query("""
            SELECT new agh.edu.pl.healthmonitoringsystem.domain.model.response.ResultOverview(
            r.id, r.patientId, r.testType, r.createdDate,
            CASE WHEN ras.id IS NOT NULL THEN true ELSE false END)
            FROM ResultEntity r
            LEFT JOIN ResultAiSelectedEntity ras ON r.id = ras.resultId
            JOIN ResultViewedEntity rv ON rv.resultId = r.id
            WHERE rv.doctorId = :userId
            ORDER BY r.createdDate DESC""")
    Page<ResultOverview> getUnviewedResults(@Param("userId") Long userId, Pageable pageable);
}
