package agh.edu.pl.healthmonitoringsystem.persistence.model.projection;

import java.time.LocalDateTime;

public record PatientReferralWithCommentProjection(
    Long id,
    Long patientId,
    String testType,
    String referralNumber,
    Boolean completed,
    Long doctorId,
    String doctorName,
    String doctorSurname,
    String comment,
    LocalDateTime modifiedDate,
    LocalDateTime createdDate)
{}
