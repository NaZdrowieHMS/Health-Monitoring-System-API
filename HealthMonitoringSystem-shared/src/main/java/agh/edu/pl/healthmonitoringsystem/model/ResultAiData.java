package agh.edu.pl.healthmonitoringsystem.model;

import agh.edu.pl.healthmonitoringsystem.response.ResultDataContent;

import java.time.LocalDateTime;

public record ResultAiData (
        Long resultId,
        String testType,
        LocalDateTime createdDate
) {}
