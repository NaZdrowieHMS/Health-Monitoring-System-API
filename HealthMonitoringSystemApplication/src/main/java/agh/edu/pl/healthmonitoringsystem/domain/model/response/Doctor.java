package agh.edu.pl.healthmonitoringsystem.domain.model.response;

import agh.edu.pl.healthmonitoringsystem.domain.exception.RequestValidationException;
import lombok.Getter;

import static agh.edu.pl.healthmonitoringsystem.domain.validator.PreconditionValidator.checkLength;
import static agh.edu.pl.healthmonitoringsystem.domain.validator.PreconditionValidator.checkNotNull;

@Getter
public class Doctor {
    private final Long id;
    private final String name;
    private final String surname;
    private final String email;
    private final String pesel;
    private final String pwz;

    @lombok.Builder(builderClassName = "Builder")
    private Doctor(Long id, String name, String surname, String email, String pesel, String pwz) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.pesel = pesel;
        this.pwz = pwz;
    }

    public static final class Builder {
        public Doctor build(){
            checkNotNull(id, () -> new RequestValidationException("Id cannot be null"));
            checkNotNull(name, () -> new RequestValidationException("Name cannot be null"));
            checkNotNull(surname, () -> new RequestValidationException("Surname cannot be null"));
            checkNotNull(email, () -> new RequestValidationException("Email cannot be null"));
            checkNotNull(pesel, () -> new RequestValidationException("PESEL cannot be null"));
            checkLength(pesel, 11, () -> new RequestValidationException("PESEL must be 11 characters"));
            checkNotNull(pwz, () -> new RequestValidationException("PWZ number cannot be null"));
            checkLength(pwz, 7, () -> new RequestValidationException("PWZ number must be 7 characters"));
            return new Doctor(id, name, surname, email, pesel, pwz);
        }
    }
}