package agh.edu.pl.healthmonitoringsystem.persistence.model.entity;

import agh.edu.pl.healthmonitoringsystem.domain.exception.RequestValidationException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static agh.edu.pl.healthmonitoringsystem.domain.validator.PreconditionValidator.checkNotNull;

@Entity
@Table(name = "ai_form_analysis")
@Getter
@Setter
public class AiFormAnalysisEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long patientId;
    private Long doctorId;
    private Long formId;
    private LocalDateTime createdDate;
    private String diagnoses;
    private String recommendations;

    public AiFormAnalysisEntity() {}

    @lombok.Builder(builderClassName = "Builder")
    public AiFormAnalysisEntity(Long id, Long patientId, Long doctorId, Long formId, String diagnoses, String recommendations,
                                LocalDateTime createdDate) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.formId = formId;
        this.diagnoses = diagnoses;
        this.recommendations = recommendations;
        this.createdDate = createdDate;
    }

    public static final class Builder {
        public AiFormAnalysisEntity build(){
            checkNotNull(patientId, () -> new RequestValidationException("Patient Id cannot be null"));
            checkNotNull(doctorId, () -> new RequestValidationException("Doctor Id cannot be null"));
            checkNotNull(formId, () -> new RequestValidationException("Form Id cannot be null"));
            checkNotNull(diagnoses, () -> new RequestValidationException("Diagnoses cannot be null"));
            checkNotNull(recommendations, () -> new RequestValidationException("Recommendations cannot be null"));
            checkNotNull(createdDate, () -> new RequestValidationException("Creation date cannot be null"));
            return new AiFormAnalysisEntity(id, patientId, doctorId, formId, diagnoses, recommendations, createdDate);
        }
    }
}

