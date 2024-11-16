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
@Table(name = "ai_prediction")
@Getter
@Setter
public class AiPredictionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long doctorId;
    private String prediction;
    private Long resultId;
    private float confidence;
    private LocalDateTime createdDate;

    public AiPredictionEntity() {}

    @lombok.Builder(builderClassName = "Builder")
    public AiPredictionEntity(Long id, Long doctorId, String prediction, Long resultId, float confidence, LocalDateTime createdDate) {
        this.id = id;
        this.doctorId = doctorId;
        this.resultId = resultId;
        this.prediction = prediction;
        this.confidence = confidence;
        this.createdDate = createdDate;
    }

    public static final class Builder {
        public AiPredictionEntity build(){
            checkNotNull(doctorId, () -> new RequestValidationException("Doctor Id cannot be null"));
            checkNotNull(resultId, () -> new RequestValidationException("Result Id cannot be null"));
            checkNotNull(prediction, () -> new RequestValidationException("Prediction cannot be null"));
            checkNotNull(confidence, () -> new RequestValidationException("Confidence cannot be null"));
            checkNotNull(createdDate, () -> new RequestValidationException("Creation date cannot be null"));
            return new AiPredictionEntity(id, doctorId, prediction, resultId, confidence, createdDate);
        }
    }
}
