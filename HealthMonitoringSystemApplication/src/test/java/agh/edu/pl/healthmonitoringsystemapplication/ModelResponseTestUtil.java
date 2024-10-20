package agh.edu.pl.healthmonitoringsystemapplication;

import agh.edu.pl.healthmonitoringsystemapplication.resources.doctors.models.DoctorResponse;
import agh.edu.pl.healthmonitoringsystemapplication.resources.patients.PatientResponse;
import agh.edu.pl.healthmonitoringsystemapplication.resources.predictions.PredictionResponse;

public class ModelResponseTestUtil {

    public static PredictionResponse.Builder predictionResponseBuilder() {
        return PredictionResponse.builder()
                .success(true)
                .prediction("benign")
                .confidence(0.87F);
    }

    public static PatientResponse.Builder patientResponseBuilder() {
        return PatientResponse.builder()
                .id(1L)
                .name("Anna")
                .surname("Nowak")
                .email("nowak@mail.com")
                .pesel("12345678909");
    }

    public static DoctorResponse.Builder doctorResponseBuilder() {
        return DoctorResponse.builder()
                .id(1L)
                .name("Anna")
                .surname("Nowak")
                .email("nowak@mail.com")
                .pesel("12345678909")
                .pwz("5425740");
    }
}
