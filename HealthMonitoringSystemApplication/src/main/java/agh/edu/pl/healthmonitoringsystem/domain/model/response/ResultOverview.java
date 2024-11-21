package agh.edu.pl.healthmonitoringsystem.domain.model.response;

import agh.edu.pl.healthmonitoringsystem.response.ResultDataContent;

import java.time.LocalDateTime;
import java.util.Optional;

public record ResultOverview(
        Long id,
        Long patientId,
        String testType,
        Optional<Boolean> aiSelected
) {
    public ResultOverview(
            Long id,
            Long patientId,
            String testType,
            Boolean aiSelected
    ) {
        this(id, patientId, testType, Optional.ofNullable(aiSelected));
    }

    public ResultOverview(
            Long id,
            Long patientId,
            String testType
    ) {
        this(id, patientId, testType, Optional.empty());
    }
}
