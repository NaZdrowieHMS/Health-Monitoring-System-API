package agh.edu.pl.healthmonitoringsystemapplication;

import agh.edu.pl.healthmonitoringsystemapplication.models.Patient;

import java.time.LocalDateTime;

public class ModelTestUtil {

    public static Patient.PatientBuilder predictionResponseBuilder() {
        return Patient.builder()
                .id(1L)
                .name("Anna")
                .surname("Nowak")
                .email("nowak@mail.com")
                .pesel("12345678909")
                .createdAt(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now());
    }
}

