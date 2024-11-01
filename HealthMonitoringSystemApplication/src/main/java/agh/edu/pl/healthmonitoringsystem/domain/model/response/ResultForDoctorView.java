package agh.edu.pl.healthmonitoringsystem.domain.model.response;

import agh.edu.pl.healthmonitoringsystem.response.ResultDataContent;

import java.time.LocalDateTime;

public record ResultForDoctorView(
        Long id,
        String testType,
        ResultDataContent content,
        Boolean ai_selected,
        Boolean viewed,
        LocalDateTime createdDate
) {}
