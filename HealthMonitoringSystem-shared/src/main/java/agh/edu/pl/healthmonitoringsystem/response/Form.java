package agh.edu.pl.healthmonitoringsystem.response;

import java.time.LocalDateTime;
import java.util.List;

public record Form (
    Long id,
    Long patientId,
    LocalDateTime createDate,
    List<FormEntry> content )
{}
