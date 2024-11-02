package agh.edu.pl.healthmonitoringsystem.domain.model.response;

import java.time.LocalDateTime;

public record Referral (
    Long referralId,
    Long patientId,
    String testType,
    String referralNumber,
    Boolean completed,
    Author doctor,
    Comment Comment,
    LocalDateTime createdDate )
{}
