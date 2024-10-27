package agh.edu.pl.healthmonitoringsystemapplication.persistence.model.projection;


import java.time.LocalDateTime;

public interface PatientReferralWithCommentProjection {
    Long getReferralId();
    Long getCommentId();
    Long getDoctorId();
    Long getPatientId();
    String getTestType();
    String getReferralNumber();
    Boolean getCompleted();
    String getCommentContent();
    LocalDateTime getModifiedDate();
}
