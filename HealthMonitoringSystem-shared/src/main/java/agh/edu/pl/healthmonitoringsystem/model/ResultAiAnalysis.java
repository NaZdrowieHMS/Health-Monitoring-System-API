package agh.edu.pl.healthmonitoringsystem.model;

import java.util.List;
import java.util.Optional;

public record ResultAiAnalysis (
    List<Long> resultIds,
    String prediction,
    Double confidence

) {}
