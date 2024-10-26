package agh.edu.pl.healthmonitoringsystemapplication.persistence.model.table;

import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDateTime;
import lombok.Setter;

import static agh.edu.pl.healthmonitoringsystemapplication.domain.validators.PreconditionValidator.checkLength;
import static agh.edu.pl.healthmonitoringsystemapplication.domain.validators.PreconditionValidator.checkNotNull;

@Entity
@Table(name = "doctor")
@Getter
@Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String pesel;
    private String pwz;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Doctor() {}

    @lombok.Builder(builderClassName = "Builder")
    public Doctor(Long id, String name, String surname, String email, String pesel, String pwz,
                  LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.pesel = pesel;
        this.pwz = pwz;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static final class Builder {
        public Doctor build(){
            checkNotNull(name, () -> new RequestValidationException("Name cannot be null"));
            checkNotNull(surname, () -> new RequestValidationException("Surname cannot be null"));
            checkNotNull(email, () -> new RequestValidationException("Email cannot be null"));
            checkNotNull(pesel, () -> new RequestValidationException("PESEL cannot be null"));
            checkLength(pesel, 11, () -> new RequestValidationException("PESEL must be 11 characters"));
            checkNotNull(pwz, () -> new RequestValidationException("PWZ number cannot be null"));
            checkLength(pwz, 7, () -> new RequestValidationException("PWZ number must be 7 characters"));
            checkNotNull(createdDate, () -> new RequestValidationException("Creation date cannot be null"));
            checkNotNull(modifiedDate, () -> new RequestValidationException("Modification date cannot be null"));
            return new Doctor(id, name, surname, email, pesel, pwz, createdDate, modifiedDate);
        }
    }
}
