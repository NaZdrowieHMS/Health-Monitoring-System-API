package agh.edu.pl.healthmonitoringsystem.persistence;

import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultCommentEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.projection.CommentWithAuthorProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultCommentRepository extends JpaRepository<ResultCommentEntity, Long> {

    @Query("""
            SELECT new agh.edu.pl.healthmonitoringsystem.persistence.model.projection.CommentWithAuthorProjection(\
            r.id, r.content, r.modifiedDate, d.id, d.name, d.surname) \
            FROM ResultCommentEntity r \
            JOIN UserEntity d ON r.doctorId = d.id \
            WHERE r.resultId = :resultId""")
    Page<CommentWithAuthorProjection> getResultCommentsWithAuthorByResultId(@Param("resultId") Long resultId, Pageable pageable);

    @Query("""
            SELECT new agh.edu.pl.healthmonitoringsystem.persistence.model.projection.CommentWithAuthorProjection(\
            r.id, r.content, r.modifiedDate, d.id, d.name, d.surname) \
            FROM ResultCommentEntity r \
            JOIN UserEntity d ON r.doctorId = d.id \
            WHERE r.id = :commentId""")
    Optional<CommentWithAuthorProjection> getResultCommentWithAuthorByCommentId(@Param("commentId") Long commentId);
}
