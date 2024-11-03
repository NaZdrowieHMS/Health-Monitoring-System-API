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
@Table(name = "doctor_patient")
@Getter
@Setter
public class DoctorPatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long patientId;
    private Long doctorId;
    private LocalDateTime createdDate;

    public DoctorPatientEntity() {}

    @lombok.Builder(builderClassName = "Builder")
    public DoctorPatientEntity(Long id, Long patientId, Long doctorId, LocalDateTime createdDate) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.createdDate = createdDate;
    }

    public static final class Builder {
        public DoctorPatientEntity build(){
            checkNotNull(patientId, () -> new RequestValidationException("Patient id cannot be null"));
            checkNotNull(doctorId, () -> new RequestValidationException("Doctor id cannot be null"));
            checkNotNull(createdDate, () -> new RequestValidationException("Creation date cannot be null"));
            return new DoctorPatientEntity(id, patientId, doctorId, createdDate);
        }
    }
}
