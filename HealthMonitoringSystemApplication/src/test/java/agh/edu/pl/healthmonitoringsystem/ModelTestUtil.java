package agh.edu.pl.healthmonitoringsystem;

import agh.edu.pl.healthmonitoringsystem.domain.models.response.Doctor;
import agh.edu.pl.healthmonitoringsystem.domain.models.response.HealthComment;
import agh.edu.pl.healthmonitoringsystem.domain.models.response.Patient;
import agh.edu.pl.healthmonitoringsystem.domain.models.response.Prediction;
import agh.edu.pl.healthmonitoringsystem.domain.models.response.Referral;
import agh.edu.pl.healthmonitoringsystem.response.Result;
import agh.edu.pl.healthmonitoringsystem.response.ResultDataContent;

import java.time.LocalDateTime;

public class ModelTestUtil {

    public static Prediction.Builder predictionBuilder() {
        return Prediction.builder()
                .success(true)
                .prediction("benign")
                .confidence(0.87F);
    }

    public static Patient.Builder patientBuilder() {
        return Patient.builder()
                .id(1L)
                .name("Anna")
                .surname("Nowak")
                .email("nowak@mail.com")
                .pesel("12345678909");
    }

    public static Doctor.Builder doctorBuilder() {
        return Doctor.builder()
                .id(1L)
                .name("Anna")
                .surname("Nowak")
                .email("nowak@mail.com")
                .pesel("12345678909")
                .pwz("5425740");
    }

    public static HealthComment.Builder healthCommentBuilder() {
        return HealthComment.builder()
                .id(2L)
                .patientId(1L)
                .content("Second comment")
                .modifiedDate(LocalDateTime.now())
                .doctor(Doctor.builder()
                        .id(2L)
                        .name("Jan")
                        .surname("Kowalski")
                        .email("jan.kowalski@mail.com")
                        .pesel("09876543211")
                        .pwz("7865431")
                        .build());
    }

    public static Referral.Builder referralBuilder() {
        return Referral.builder()
                .modifiedDate(LocalDateTime.now())
                .referralId(1L)
                .doctorId(1L)
                .patientId(1L)
                .testType("Test")
                .referralNumber("123")
                .completed(true);
    }

    public static ResultDataContent resultDataContent() {
        return new ResultDataContent(
                "Some data",
                "Blood"
        );
    }
}
