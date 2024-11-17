package agh.edu.pl.healthmonitoringsystem.response;

import java.time.LocalDateTime;

public record Prediction (
    Long id,
    Long resultId,
    String prediction,
    Double confidence,
    LocalDateTime createdDate )
{}