package agh.edu.pl.healthmonitoringsystem.persistence.model.entity;

import agh.edu.pl.healthmonitoringsystem.domain.exception.RequestValidationException;
import agh.edu.pl.healthmonitoringsystem.response.ResultDataType;
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
@Table(name = "result")
@Getter
@Setter
public class ResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long patientId;
    private String testType;
    private ResultDataType dataType;
    private String data;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public ResultEntity() {}

    @lombok.Builder(builderClassName = "Builder")
    public ResultEntity(Long id, Long patientId, String testType, ResultDataType dataType, String data,
                        LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.patientId = patientId;
        this.testType = testType;
        this.dataType = dataType;
        this.data = data;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static final class Builder {
        public ResultEntity build(){
            checkNotNull(patientId, () -> new RequestValidationException("Patient Id cannot be null"));
            checkNotNull(testType, () -> new RequestValidationException("Test type cannot be null"));
            checkNotNull(dataType, () -> new RequestValidationException("Data type cannot be null"));
            checkNotNull(data, () -> new RequestValidationException("Data cannot be null"));
            checkNotNull(createdDate, () -> new RequestValidationException("Creation date cannot be null"));
            checkNotNull(modifiedDate, () -> new RequestValidationException("Modification date cannot be null"));
            return new ResultEntity(id, patientId, testType, dataType, data, createdDate, modifiedDate);
        }
    }
}
