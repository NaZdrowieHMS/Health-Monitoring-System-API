package agh.edu.pl.healthmonitoringsystem;

import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.AiPredictionEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.AiPredictionCommentEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.DoctorEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.HealthCommentEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.PatientEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ReferralEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ReferralCommentEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultEntity;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultCommentEntity;

import java.time.LocalDateTime;

public class ModelEntityTestUtil {

    public static PatientEntity.Builder patientBuilder() {
        return PatientEntity.builder()
                .id(1L)
                .name("Anna")
                .surname("Nowak")
                .email("nowak@mail.com")
                .pesel("12345678909")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now());
    }

    public static DoctorEntity.Builder doctorBuilder() {
        return DoctorEntity.builder()
                .id(1L)
                .name("Anna")
                .surname("Nowak")
                .email("nowak@mail.com")
                .pesel("12345678909")
                .pwz("5425740")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now());
    }

    public static AiPredictionEntity.Builder aiPredictionBuilder() {
        return AiPredictionEntity.builder()
                .id(1L)
                .doctorId(2L)
                .patientId(3L)
                .content("Prediction content")
                .createdDate(LocalDateTime.now());
    }

    public static AiPredictionCommentEntity.Builder aiPredictionCommentBuilder() {
        return AiPredictionCommentEntity.builder()
                .id(1L)
                .doctorId(2L)
                .patientId(3L)
                .content("Comment content")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now());
    }

    public static HealthCommentEntity.Builder healthBuilder() {
        return HealthCommentEntity.builder()
                .id(1L)
                .doctorId(2L)
                .patientId(3L)
                .content("Health content")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now());
    }

    public static ReferralEntity.Builder referralBuilder() {
        return ReferralEntity.builder()
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

    public static ReferralCommentEntity.Builder referralCommentBuilder() {
        return ReferralCommentEntity.builder()
                .id(1L)
                .doctorId(2L)
                .content("Sample content")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now());
    }

    public static ResultEntity.Builder resultBuilder() {
        return ResultEntity.builder()
                .id(1L)
                .patientId(2L)
                .testType("Blood Test")
                .content("Test result content")
                .aiSelected(false)
                .viewed(false)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now());
    }

    public static ResultCommentEntity.Builder resultCommentBuilder() {
        return ResultCommentEntity.builder()
                .id(1L)
                .resultId(2L)
                .doctorId(3L)
                .content("Comment content")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now());
    }
}

