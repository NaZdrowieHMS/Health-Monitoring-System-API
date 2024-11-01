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
@Table(name = "result_ai_selected")
@Getter
@Setter
public class ResultAiSelectedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long patientId;
    private Long doctorId;
    private Long resultId;
    private LocalDateTime createdDate;

    public ResultAiSelectedEntity() {}

    @lombok.Builder(builderClassName = "Builder")
    public ResultAiSelectedEntity(Long id, Long patientId, Long doctorId, Long resultId, LocalDateTime createdDate) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.resultId = resultId;
        this.createdDate = createdDate;
    }

    public static final class Builder {
        public ResultAiSelectedEntity build(){
            checkNotNull(patientId, () -> new RequestValidationException("Patient Id cannot be null"));
            checkNotNull(doctorId, () -> new RequestValidationException("Doctor Id cannot be null"));
            checkNotNull(resultId, () -> new RequestValidationException("Result Id cannot be null"));
            checkNotNull(createdDate, () -> new RequestValidationException("Creation date cannot be null"));
            return new ResultAiSelectedEntity(id, patientId, doctorId, resultId, createdDate);
        }
    }
}
