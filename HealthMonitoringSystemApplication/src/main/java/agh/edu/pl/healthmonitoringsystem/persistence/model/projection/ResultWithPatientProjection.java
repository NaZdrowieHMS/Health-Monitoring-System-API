package agh.edu.pl.healthmonitoringsystem.persistence.model.projection;

import java.time.LocalDateTime;

public record ResultWithPatientProjection(
    Long id,
    Long patientId,
    String name,
    String surname,
    String email,
    String pesel,
    String testType,
    String dataType,
    String data,
    LocalDateTime createdDate )
{}
