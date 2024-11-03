package agh.edu.pl.healthmonitoringsystem.persistence.model.projection;


import java.time.LocalDateTime;

public interface PatientReferralWithCommentProjection {
    Long getId();
    Long getPatientId();
    String getTestType();
    String getReferralNumber();
    Boolean getCompleted();
    Long getDoctorId();
    String getDoctorName();
    String getDoctorSurname();
    String getComment();
    LocalDateTime getModifiedDate();
    LocalDateTime getCreatedDate();
}
