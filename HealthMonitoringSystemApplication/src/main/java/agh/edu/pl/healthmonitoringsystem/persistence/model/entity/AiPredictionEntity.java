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
    private Long patientId;
    private String content;
    private LocalDateTime createdDate;

    public AiPredictionEntity() {}

    @lombok.Builder(builderClassName = "Builder")
    public AiPredictionEntity(Long id, Long doctorId, Long patientId, String content, LocalDateTime createdDate) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.content = content;
        this.createdDate = createdDate;
    }

    public static final class Builder {
        public AiPredictionEntity build(){
            checkNotNull(doctorId, () -> new RequestValidationException("Doctor Id cannot be null"));
            checkNotNull(patientId, () -> new RequestValidationException("PatientEntity Id cannot be null"));
            checkNotNull(content, () -> new RequestValidationException("Content cannot be null"));
            checkNotNull(createdDate, () -> new RequestValidationException("Creation date cannot be null"));
            return new AiPredictionEntity(id, doctorId, patientId, content, createdDate);
        }
    }
}
