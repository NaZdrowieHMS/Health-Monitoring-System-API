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
@Table(name = "health_comment")
@Getter
@Setter
public class HealthCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long doctorId;
    private Long patientId;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public HealthCommentEntity() {}

    @lombok.Builder(builderClassName = "Builder")
    public HealthCommentEntity(Long id, Long doctorId, Long patientId, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static final class Builder {
        public HealthCommentEntity build(){
            checkNotNull(doctorId, () -> new RequestValidationException("Doctor Id cannot be null"));
            checkNotNull(patientId, () -> new RequestValidationException("Patient Id cannot be null"));
            checkNotNull(content, () -> new RequestValidationException("Content cannot be null"));
            checkNotNull(createdDate, () -> new RequestValidationException("Creation date cannot be null"));
            checkNotNull(modifiedDate, () -> new RequestValidationException("Modification date cannot be null"));
            return new HealthCommentEntity(id, doctorId, patientId, content, createdDate, modifiedDate);
        }
    }
}
