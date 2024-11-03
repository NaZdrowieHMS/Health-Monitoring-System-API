package agh.edu.pl.healthmonitoringsystem.domain.model.response;

import java.time.LocalDateTime;

public record Referral (
    Long id,
    Long patientId,
    String testType,
    String referralNumber,
    Boolean completed,
    Author doctor,
    Comment comment,
    LocalDateTime createdDate )
{}
