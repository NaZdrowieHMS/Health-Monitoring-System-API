package agh.edu.pl.healthmonitoringsystemapplication.domain.models.response;

import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import lombok.Getter;

import java.time.LocalDateTime;

import static agh.edu.pl.healthmonitoringsystemapplication.domain.validators.PreconditionValidator.checkNotNull;


@Getter
public class HealthResponse {
    private final Long id;
    private final Long patientId;
    private final DoctorResponse doctor;
    private final LocalDateTime modifiedDate;
    private final String content;

    @lombok.Builder(builderClassName = "Builder")
    private HealthResponse(Long id, Long patientId, DoctorResponse doctor, LocalDateTime modifiedDate, String content) {
        this.id = id;
        this.patientId = patientId;
        this.doctor = doctor;
        this.modifiedDate = modifiedDate;
        this.content = content;
    }

    public static final class Builder {
        public HealthResponse build(){
            checkNotNull(id, () -> new RequestValidationException("Id cannot be null"));
            checkNotNull(patientId, () -> new RequestValidationException("Patient id cannot be null"));
            checkNotNull(doctor, () -> new RequestValidationException("Doctor cannot be null"));
            checkNotNull(modifiedDate, () -> new RequestValidationException("Modification date cannot be null"));
            checkNotNull(content, () -> new RequestValidationException("Content cannot be null"));
            return new HealthResponse(id, patientId, doctor, modifiedDate, content);
        }
    }
}


