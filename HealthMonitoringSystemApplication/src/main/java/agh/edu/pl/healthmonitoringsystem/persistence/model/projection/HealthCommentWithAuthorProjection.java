package agh.edu.pl.healthmonitoringsystem.persistence.model.projection;

import java.time.LocalDateTime;

public interface HealthCommentWithAuthorProjection {
    Long getHealthCommentId();
    Long getPatientId();
    String getContent();
    LocalDateTime getModifiedDate();
    Long getDoctorId();
    String getDoctorName();
    String getDoctorSurname();
}

