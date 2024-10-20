package agh.edu.pl.healthmonitoringsystemapplication;

import agh.edu.pl.healthmonitoringsystemapplication.databaseModels.Doctor;
import agh.edu.pl.healthmonitoringsystemapplication.databaseModels.Patient;

import java.time.LocalDateTime;

public class ModelTestUtil {

    public static Patient.PatientBuilder patientBuilder() {
        return Patient.builder()
                .id(1L)
                .name("Anna")
                .surname("Nowak")
                .email("nowak@mail.com")
                .pesel("12345678909")
                .createdAt(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now());
    }

    public static Doctor.DoctorBuilder doctorBuilder() {
        return Doctor.builder()
                .id(1L)
                .name("Anna")
                .surname("Nowak")
                .email("nowak@mail.com")
                .pesel("12345678909")
                .pwz("5425740")
                .createdAt(LocalDateTime.now());
    }
}

