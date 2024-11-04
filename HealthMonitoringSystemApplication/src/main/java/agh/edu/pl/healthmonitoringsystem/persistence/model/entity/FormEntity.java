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
@Table(name = "health_form")
@Getter
@Setter
public class FormEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long patientId;
    private LocalDateTime createdDate;

    public FormEntity() {}

    @lombok.Builder(builderClassName = "Builder")
    public FormEntity(Long id, Long patientId, LocalDateTime createdDate) {
        this.id = id;
        this.patientId = patientId;
        this.createdDate = createdDate;
    }

    public static final class Builder {
        public FormEntity build(){
            checkNotNull(patientId, () -> new RequestValidationException("Patient Id cannot be null"));
            checkNotNull(createdDate, () -> new RequestValidationException("Creation date cannot be null"));
            return new FormEntity(id, patientId, createdDate);
        }
    }
}
