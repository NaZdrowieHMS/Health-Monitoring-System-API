package agh.edu.pl.healthmonitoringsystem.response;

import agh.edu.pl.healthmonitoringsystem.enums.PredictionRequestStatus;

import java.time.LocalDateTime;
import java.util.List;

public record PredictionSummary(
        Long id,
        PredictionRequestStatus status,

        Long patientId,
        Long doctorId,
        List<Long> resultIds,

        String prediction,
        Double confidence,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate )
{}