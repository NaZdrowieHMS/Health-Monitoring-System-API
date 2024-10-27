package agh.edu.pl.healthmonitoringsystemapplication.domain.models.response;

import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import lombok.Getter;

import java.time.LocalDateTime;

import static agh.edu.pl.healthmonitoringsystemapplication.domain.validators.PreconditionValidator.checkNotNull;

@Getter
public class Result {
    private final Long id;
    private final Long patientId;
    private final String testType;
    private final ResultDataContent content;
    private final LocalDateTime createdDate;

    @lombok.Builder(builderClassName = "Builder")
    private Result(Long id, Long patientId, String testType, ResultDataContent content, LocalDateTime createdDate) {
        this.id = id;
        this.patientId = patientId;
        this.testType = testType;
        this.content = content;
        this.createdDate = createdDate;
    }

    public static final class Builder {
        public Result build(){
            checkNotNull(id, () -> new RequestValidationException("Id cannot be null"));
            checkNotNull(patientId, () -> new RequestValidationException("PatientEntity id cannot be null"));
            checkNotNull(testType, () -> new RequestValidationException("Test type cannot be null"));
            checkNotNull(content, () -> new RequestValidationException("Content cannot be null"));
            checkNotNull(createdDate, () -> new RequestValidationException("Creation date cannot be null"));
            return new Result(id, patientId, testType, content, createdDate);
        }
    }
}
