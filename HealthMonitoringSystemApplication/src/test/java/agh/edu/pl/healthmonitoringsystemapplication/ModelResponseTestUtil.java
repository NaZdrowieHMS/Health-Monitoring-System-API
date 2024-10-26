package agh.edu.pl.healthmonitoringsystemapplication;

import agh.edu.pl.healthmonitoringsystemapplication.domain.models.response.DoctorResponse;
import agh.edu.pl.healthmonitoringsystemapplication.domain.models.response.HealthResponse;
import agh.edu.pl.healthmonitoringsystemapplication.domain.models.response.PatientResponse;
import agh.edu.pl.healthmonitoringsystemapplication.domain.models.response.PredictionResponse;
import agh.edu.pl.healthmonitoringsystemapplication.domain.models.response.ReferralResponse;

import java.time.LocalDateTime;

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

    public static HealthResponse.Builder healthResponseBuilder() {
        return HealthResponse.builder()
                .id(2L)
                .patientId(1L)
                .content("Second comment")
                .modifiedDate(LocalDateTime.now())
                .doctor(DoctorResponse.builder()
                        .id(2L)
                        .name("Jan")
                        .surname("Kowalski")
                        .email("jan.kowalski@mail.com")
                        .pesel("09876543211")
                        .pwz("7865431")
                        .build());
    }

    public static ReferralResponse.Builder referralResponseBuilder() {
        return ReferralResponse.builder()
                .modifiedDate(LocalDateTime.now())
                .referralId(1L)
                .doctorId(1L)
                .patientId(1L)
                .testType("Test")
                .referralNumber("123")
                .completed(true);
    }
}
