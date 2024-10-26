package agh.edu.pl.healthmonitoringsystemapplication.persistence.model.table;


import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static agh.edu.pl.healthmonitoringsystemapplication.domain.validators.PreconditionValidator.checkNotNull;

@Entity
@Table(name = "ai_prediction")
@Getter
@Setter
public class AiPrediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long doctorId;
    private Long patientId;
    private String content;
    private LocalDateTime createdDate;

    public AiPrediction() {}

    @lombok.Builder(builderClassName = "Builder")
    public AiPrediction(Long id, Long doctorId, Long patientId, String content, LocalDateTime createdDate) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.content = content;
        this.createdDate = createdDate;
    }

    public static final class Builder {
        public AiPrediction build(){
            checkNotNull(doctorId, () -> new RequestValidationException("Doctor Id cannot be null"));
            checkNotNull(patientId, () -> new RequestValidationException("Patient Id cannot be null"));
            checkNotNull(content, () -> new RequestValidationException("Content cannot be null"));
            checkNotNull(createdDate, () -> new RequestValidationException("Creation date cannot be null"));
            return new AiPrediction(id, doctorId, patientId, content, createdDate);
        }
    }
}
