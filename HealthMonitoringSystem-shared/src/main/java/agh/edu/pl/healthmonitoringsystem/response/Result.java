package agh.edu.pl.healthmonitoringsystem.response;

import java.time.LocalDateTime;

public record Result(
        Long id,
        Long patientId,
        String testType,
        LocalDateTime createdDate,
        ResultDataContent content
) {}
