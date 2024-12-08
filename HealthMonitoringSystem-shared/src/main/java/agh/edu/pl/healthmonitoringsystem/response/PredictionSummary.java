package agh.edu.pl.healthmonitoringsystem.response;

import agh.edu.pl.healthmonitoringsystem.enums.PredictionRequestStatus;
import agh.edu.pl.healthmonitoringsystem.model.FormAiAnalysis;
import agh.edu.pl.healthmonitoringsystem.model.ResultAiAnalysis;

import java.time.LocalDateTime;

public record PredictionSummary(
        Long id,
        PredictionRequestStatus status,
        Long patientId,
        Long doctorId,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate,
        ResultAiAnalysis resultAiAnalysis,
        FormAiAnalysis formAiAnalysis
)
{}