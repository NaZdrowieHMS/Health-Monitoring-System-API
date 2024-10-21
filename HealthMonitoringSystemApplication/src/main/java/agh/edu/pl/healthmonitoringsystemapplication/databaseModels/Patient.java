package agh.edu.pl.healthmonitoringsystemapplication.databaseModels;

import agh.edu.pl.healthmonitoringsystemapplication.exceptions.RequestValidationException;
import lombok.Getter;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import static agh.edu.pl.healthmonitoringsystemapplication.validators.PreconditionValidator.checkLength;
import static agh.edu.pl.healthmonitoringsystemapplication.validators.PreconditionValidator.checkNotNull;

@Table("patient")
@Getter
public class Patient {
    @Id
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
