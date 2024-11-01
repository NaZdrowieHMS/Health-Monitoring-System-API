package agh.edu.pl.healthmonitoringsystem.domain.model.response;

import agh.edu.pl.healthmonitoringsystem.response.ResultDataContent;

import java.time.LocalDateTime;

public record ResultWithPatientData(
        Long id,
        Patient patient,
        String testType,
        ResultDataContent content,
        LocalDateTime createdDate
) {}
