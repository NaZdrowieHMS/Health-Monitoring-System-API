package agh.edu.pl.healthmonitoringsystemapplication.persistence.model;

import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import lombok.Setter;

import static agh.edu.pl.healthmonitoringsystemapplication.domain.validators.PreconditionValidator.checkLength;
import static agh.edu.pl.healthmonitoringsystemapplication.domain.validators.PreconditionValidator.checkNotNull;

@Entity
@Table(name = "patient")
@Getter
@Setter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String pesel;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated; //updatedAt TODO: rename it here, in the code and in database

    public Patient() {}

    @lombok.Builder(builderClassName = "Builder")
    public Patient(Long id, String name, String surname, String email, String pesel, LocalDateTime createdAt, LocalDateTime lastUpdated) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.pesel = pesel;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
    }

    public static final class Builder {
        public Patient build(){
            checkNotNull(name, () -> new RequestValidationException("Name cannot be null"));
            checkNotNull(surname, () -> new RequestValidationException("Surname cannot be null"));
            checkNotNull(email, () -> new RequestValidationException("Email cannot be null"));
            checkNotNull(pesel, () -> new RequestValidationException("PESEL cannot be null"));
            checkLength(pesel, 11, () -> new RequestValidationException("PESEL must be 11 characters"));
            checkNotNull(createdAt, () -> new RequestValidationException("Creation date cannot be null"));
            checkNotNull(lastUpdated, () -> new RequestValidationException("Last update date cannot be null"));
            return new Patient(id, name, surname, email, pesel, createdAt, lastUpdated);
        }
    }
}
