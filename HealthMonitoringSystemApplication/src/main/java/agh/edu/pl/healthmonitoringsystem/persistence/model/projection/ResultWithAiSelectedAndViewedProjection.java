package agh.edu.pl.healthmonitoringsystem.persistence.model.projection;

import java.time.LocalDateTime;

public record ResultWithAiSelectedAndViewedProjection(
    Long id,
    Long patientId,
    String testType,
    String dataType,
    String data,
    LocalDateTime createdDate,
    Boolean aiSelected,
    Boolean viewed )
{}
