package agh.edu.pl.healthmonitoringsystem.model;

import java.time.LocalDateTime;
import java.util.List;

public record FormAiAnalysis(
        Long formId,
        List<String> diagnoses,
        List<String> recommendations,
        LocalDateTime createdDate
) {}
