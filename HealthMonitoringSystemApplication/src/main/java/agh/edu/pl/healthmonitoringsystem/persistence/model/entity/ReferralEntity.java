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
@Table(name = "referral")
@Getter
@Setter
public class ReferralEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long doctorId;
    private Long patientId;
    private String testType;
    private String number;
    private Boolean completed = false;
    private String comment = null;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public ReferralEntity() {}

    @lombok.Builder(builderClassName = "Builder")
    public ReferralEntity(Long id, Long doctorId, Long patientId, String testType, String number, Boolean completed,
                          String comment, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.testType = testType;
        this.number = number;
        this.completed = completed;
        this.comment = comment;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static final class Builder {
        public ReferralEntity build(){
            checkNotNull(doctorId, () -> new RequestValidationException("Doctor Id cannot be null"));
            checkNotNull(patientId, () -> new RequestValidationException("Patient Id cannot be null"));
            checkNotNull(testType, () -> new RequestValidationException("Test type cannot be null"));
            checkNotNull(number, () -> new RequestValidationException("Referral number cannot be null"));
            checkNotNull(createdDate, () -> new RequestValidationException("Creation date cannot be null"));
            checkNotNull(modifiedDate, () -> new RequestValidationException("Modification date cannot be null"));
            return new ReferralEntity(id, doctorId, patientId, testType, number, completed, comment, createdDate,
                    modifiedDate);
        }
    }
}