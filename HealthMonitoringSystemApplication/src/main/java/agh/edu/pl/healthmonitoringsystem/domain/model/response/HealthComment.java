package agh.edu.pl.healthmonitoringsystem.domain.model.response;

import agh.edu.pl.healthmonitoringsystem.domain.exception.RequestValidationException;
import lombok.Getter;

import java.time.LocalDateTime;

import static agh.edu.pl.healthmonitoringsystem.domain.validator.PreconditionValidator.checkNotNull;


@Getter
public class HealthComment {
    private final Long id;
    private final Long patientId;
    private final Doctor doctor;
    private final LocalDateTime modifiedDate;
    private final String content;

    @lombok.Builder(builderClassName = "Builder")
    private HealthComment(Long id, Long patientId, Doctor doctor, LocalDateTime modifiedDate, String content) {
        this.id = id;
        this.patientId = patientId;
        this.doctor = doctor;
        this.modifiedDate = modifiedDate;
        this.content = content;
    }

    public static final class Builder {
        public HealthComment build(){
            checkNotNull(id, () -> new RequestValidationException("Id cannot be null"));
            checkNotNull(patientId, () -> new RequestValidationException("PatientEntity id cannot be null"));
            checkNotNull(doctor, () -> new RequestValidationException("Doctor cannot be null"));
            checkNotNull(modifiedDate, () -> new RequestValidationException("Modification date cannot be null"));
            checkNotNull(content, () -> new RequestValidationException("Content cannot be null"));
            return new HealthComment(id, patientId, doctor, modifiedDate, content);
        }
    }
}


