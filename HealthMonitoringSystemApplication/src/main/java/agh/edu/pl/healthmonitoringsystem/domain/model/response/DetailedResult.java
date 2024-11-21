package agh.edu.pl.healthmonitoringsystem.domain.model.response;

import agh.edu.pl.healthmonitoringsystem.response.ResultDataContent;

import java.time.LocalDateTime;
import java.util.Optional;

public record DetailedResult (
        Long id,
        Long patientId,
        String testType,
        ResultDataContent content,
        LocalDateTime createdDate,
        Optional<Boolean> aiSelected,
        Optional<Boolean> viewed
) {
    public DetailedResult(
        Long id,
        Long patientId,
        String testType,
        ResultDataContent content,
        LocalDateTime createdDate,
        Boolean aiSelected,
        Boolean viewed
    ) {
    this(id, patientId, testType, content, createdDate, Optional.ofNullable(aiSelected), Optional.ofNullable(viewed));
    }

    public DetailedResult(
            Long id,
            Long patientId,
            String testType,
            ResultDataContent content,
            LocalDateTime createdDate
    ) {
        this(id, patientId, testType, content, createdDate, Optional.empty(), Optional.empty());
    }
}
