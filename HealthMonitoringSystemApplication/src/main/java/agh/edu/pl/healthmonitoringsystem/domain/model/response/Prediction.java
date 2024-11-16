package agh.edu.pl.healthmonitoringsystem.domain.model.response;

import java.time.LocalDateTime;

public record Prediction (
    Long id,
    Long resultId,
    String prediction,
    Double confidence,
    LocalDateTime createdDate )
{}