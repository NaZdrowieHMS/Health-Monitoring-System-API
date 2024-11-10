package agh.edu.pl.healthmonitoringsystem.response;

import java.time.LocalDateTime;
import java.util.List;

public record AiFormAnalysis(
        Long id,
        Long patientId,
        Long formId,
        List<String> diagnoses,
        List<String> recommendations,
        LocalDateTime createdDate
) {}
