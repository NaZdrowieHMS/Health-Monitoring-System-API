package agh.edu.pl.healthmonitoringsystemapplication;

import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.AiPrediction;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.AiPredictionComment;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.Doctor;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.Health;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.Patient;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.Referral;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.ReferralComment;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.Result;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.ResultComment;

import java.time.LocalDateTime;

public class ModelTestUtil {

    public static Patient.Builder patientBuilder() {
        return Patient.builder()
                .id(1L)
                .name("Anna")
                .surname("Nowak")
                .email("nowak@mail.com")
                .pesel("12345678909")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now());
    }

    public static Doctor.Builder doctorBuilder() {
        return Doctor.builder()
                .id(1L)
                .name("Anna")
                .surname("Nowak")
                .email("nowak@mail.com")
                .pesel("12345678909")
                .pwz("5425740")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now());
    }

    public static AiPrediction.Builder aiPredictionBuilder() {
        return AiPrediction.builder()
                .id(1L)
                .doctorId(2L)
                .patientId(3L)
                .content("Prediction content")
                .createdDate(LocalDateTime.now());
    }

    public static AiPredictionComment.Builder aiPredictionCommentBuilder() {
        return AiPredictionComment.builder()
                .id(1L)
                .doctorId(2L)
                .patientId(3L)
                .content("Comment content")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now());
    }

    public static Health.Builder healthBuilder() {
        return Health.builder()
                .id(1L)
                .doctorId(2L)
                .patientId(3L)
                .content("Health content")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now());
    }

    public static Referral.Builder referralBuilder() {
        return Referral.builder()
                .id(1L)
                .doctorId(2L)
                .patientId(3L)
                .commentId(null)
                .testType("Blood Test")
                .number("12345")
                .completed(false)
                .completedDate(null)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now());
    }

    public static ReferralComment.Builder referralCommentBuilder() {
        return ReferralComment.builder()
                .id(1L)
                .doctorId(2L)
                .content("Sample content")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now());
    }

    public static Result.Builder resultBuilder() {
        return Result.builder()
                .id(1L)
                .patientId(2L)
                .testType("Blood Test")
                .content("Test result content")
                .aiSelected(false)
                .viewed(false)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now());
    }

    public static ResultComment.Builder resultCommentBuilder() {
        return ResultComment.builder()
                .id(1L)
                .resultId(2L)
                .doctorId(3L)
                .content("Comment content")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now());
    }
}

