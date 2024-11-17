package agh.edu.pl.healthmonitoringsystem.persistence.model.entity;

import agh.edu.pl.healthmonitoringsystem.domain.exception.RequestValidationException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static agh.edu.pl.healthmonitoringsystem.domain.validator.PreconditionValidator.checkNotNull;

@Entity
@Table(name = "ai_prediction_summary")
@Getter
@Setter
public class AiPredictionSummaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long patientId;
    private Long doctorId;
    private String status;
    private String prediction = "";
    private Double confidence = null;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public AiPredictionSummaryEntity() {}

    @lombok.Builder(builderClassName = "Builder")
    public AiPredictionSummaryEntity(Long id, Long patientId, Long doctorId, String status, String prediction, Double confidence, LocalDateTime createdDate,
                                     LocalDateTime modifiedDate) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.status = status;
        this.prediction = prediction;
        this.confidence = confidence;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static final class Builder {
        public AiPredictionSummaryEntity build(){
            checkNotNull(patientId, () -> new RequestValidationException("Patient ID cannot be null"));
            checkNotNull(doctorId, () -> new RequestValidationException("Doctor ID cannot be null"));
            checkNotNull(status, () -> new RequestValidationException("Status cannot be null"));
//            checkNotNull(prediction, () -> new RequestValidationException("Prediction cannot be null"));
//            checkNotNull(confidence, () -> new RequestValidationException("Confidence cannot be null"));
//            if (confidence < 0 || confidence > 1){
//                throw new RequestValidationException("Confidence must be in the range from 0 to 1");
//            }
            checkNotNull(createdDate, () -> new RequestValidationException("Creation date cannot be null"));
            checkNotNull(modifiedDate, () -> new RequestValidationException("Modified date cannot be null"));
            return new AiPredictionSummaryEntity(id, patientId, doctorId, status, prediction, confidence, createdDate, modifiedDate);
        }
    }
}
