package agh.edu.pl.healthmonitoringsystem.persistence;

import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.AiPredictionCommentEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.CommentWithAuthorProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PredictionCommentRepository extends JpaRepository<AiPredictionCommentEntity, Long> {

    @Query("""
            SELECT new agh.edu.pl.healthmonitoringsystem.persistence.model.projection.CommentWithAuthorProjection(\
            p.id, p.content, p.modifiedDate, d.id, d.name, d.surname) \
            FROM AiPredictionCommentEntity p \
            JOIN UserEntity d ON p.doctorId = d.id \
            WHERE p.predictionSummaryId = :predictionId""")
    Page<CommentWithAuthorProjection> getPredictionCommentsWithAuthorByPredictionId(@Param("predictionId") Long predictionId, Pageable pageable);

    @Query("""
            SELECT new agh.edu.pl.healthmonitoringsystem.persistence.model.projection.CommentWithAuthorProjection(\
            p.id, p.content, p.modifiedDate, d.id, d.name, d.surname) \
            FROM AiPredictionCommentEntity p \
            JOIN UserEntity d ON p.doctorId = d.id \
            WHERE p.id = :commentId""")
    Optional<CommentWithAuthorProjection> getPredictionCommentWithAuthorByCommentId(@Param("commentId") Long commentId);
}
