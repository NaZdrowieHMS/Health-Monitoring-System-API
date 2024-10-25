package agh.edu.pl.healthmonitoringsystemapplication;

import agh.edu.pl.healthmonitoringsystemapplication.domain.models.response.DoctorResponse;
import agh.edu.pl.healthmonitoringsystemapplication.domain.models.response.PatientResponse;
import agh.edu.pl.healthmonitoringsystemapplication.domain.models.response.PredictionResponse;

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
