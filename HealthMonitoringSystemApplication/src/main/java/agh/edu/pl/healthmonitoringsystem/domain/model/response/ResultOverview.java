package agh.edu.pl.healthmonitoringsystem.domain.model.response;

import agh.edu.pl.healthmonitoringsystem.response.ResultDataContent;

import java.time.LocalDateTime;
import java.util.Optional;

public record ResultOverview(
        Long id,
        Long patientId,
        String testType,
        LocalDateTime createdDate,
        Optional<Boolean> aiSelected
) {
    public ResultOverview(
            Long id,
            Long patientId,
            String testType,
            LocalDateTime createdDate,
            Boolean aiSelected
    ) {
        this(id, patientId, testType, createdDate, Optional.ofNullable(aiSelected));
    }

    public ResultOverview(
            Long id,
            Long patientId,
            String testType,
            LocalDateTime createdDate
    ) {
        this(id, patientId, testType, createdDate, Optional.empty());
    }
}
