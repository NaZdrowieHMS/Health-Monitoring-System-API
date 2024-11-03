package agh.edu.pl.healthmonitoringsystem.persistence.model.projection;

import java.time.LocalDateTime;

public interface ResultWithPatientDataProjection {
    Long getId();
    Long getPatientId();
    String getName();
    String getSurname();
    String getEmail();
    String getPesel();
    String getTestType();
    String getDataType();
    String getData();
    LocalDateTime getCreatedDate();;
}
