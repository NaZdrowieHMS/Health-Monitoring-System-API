package agh.edu.pl.healthmonitoringsystem.persistence.model.entity;

import agh.edu.pl.healthmonitoringsystem.domain.exception.RequestValidationException;
import agh.edu.pl.healthmonitoringsystem.enums.PredictionRequestStatus;
import agh.edu.pl.healthmonitoringsystem.model.FormAiAnalysis;
import agh.edu.pl.healthmonitoringsystem.model.ResultAiAnalysis;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

import static agh.edu.pl.healthmonitoringsystem.domain.validator.PreconditionValidator.checkNotNull;

@Entity
@Table(name = "prediction_summary")
@Getter
@Setter

public class PredictionSummaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PredictionRequestStatus status;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "modified_date", nullable = false)
    private LocalDateTime modifiedDate;

    @Column(name = "result_ai_analysis", columnDefinition = "json")
    private String resultAiAnalysis;

    @Column(name = "form_ai_analysis", columnDefinition = "json")
    private String formAiAnalysis;

    public PredictionSummaryEntity() {}

    @lombok.Builder(builderClassName = "Builder")
    public PredictionSummaryEntity(Long id, Long patientId, Long doctorId, PredictionRequestStatus status, LocalDateTime createdDate,
                                     LocalDateTime modifiedDate) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.status = status;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static final class Builder {
        public PredictionSummaryEntity build(){
            checkNotNull(patientId, () -> new RequestValidationException("Patient ID cannot be null"));
            checkNotNull(doctorId, () -> new RequestValidationException("Doctor ID cannot be null"));
            checkNotNull(status, () -> new RequestValidationException("Status cannot be null"));
            checkNotNull(createdDate, () -> new RequestValidationException("Creation date cannot be null"));
            checkNotNull(modifiedDate, () -> new RequestValidationException("Modified date cannot be null"));
            return new PredictionSummaryEntity(id, patientId, doctorId, status, createdDate, modifiedDate);
        }
    }
}
