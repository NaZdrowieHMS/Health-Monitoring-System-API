package agh.edu.pl.healthmonitoringsystem.model;

import java.util.List;
import java.util.Optional;

public record ResultAiAnalysis (
    List<ResultAiData> results,
    String prediction,
    Double confidence

) {}
