package agh.edu.pl.healthmonitoringsystem.persistence.model.projection;

import java.time.LocalDateTime;

public record CommentWithAuthorProjection(
    Long commentId,
    String content,
    LocalDateTime modifiedDate,
    Long doctorId,
    String doctorName,
    String doctorSurname )
{}

