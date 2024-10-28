package agh.edu.pl.healthmonitoringsystem.response;

import java.time.LocalDateTime;

public record Result(
        Long id,
        Long patientId,
        String testType,
        ResultDataContent content,
        LocalDateTime createdDate
) {}
