package agh.edu.pl.healthmonitoringsystem.request;

import java.util.List;

public record PredictionSummaryRequest(
    Long patientId,
    Long doctorId,
    List<Long> resultIds)
{}
