package agh.edu.pl.healthmonitoringsystemapplication.persistence.model.entity;

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
@Table(name = "result")
@Getter
@Setter
public class ResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long patientId;
    private String testType;
    private String content;
    private Boolean aiSelected = false;
    private Boolean viewed = false;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public ResultEntity() {}

    @lombok.Builder(builderClassName = "Builder")
    public ResultEntity(Long id, Long patientId, String testType, String content, Boolean aiSelected, Boolean viewed,
                        LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.patientId = patientId;
        this.testType = testType;
        this.content = content;
        this.aiSelected = aiSelected;
        this.viewed = viewed;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static final class Builder {
        public ResultEntity build(){
            checkNotNull(patientId, () -> new RequestValidationException("PatientEntity Id cannot be null"));
            checkNotNull(testType, () -> new RequestValidationException("Test type cannot be null"));
            checkNotNull(content, () -> new RequestValidationException("Content cannot be null"));
            checkNotNull(createdDate, () -> new RequestValidationException("Creation date cannot be null"));
            checkNotNull(modifiedDate, () -> new RequestValidationException("Modification date cannot be null"));
            return new ResultEntity(id, patientId, testType, content, aiSelected, viewed, createdDate, modifiedDate);
        }
    }
}
