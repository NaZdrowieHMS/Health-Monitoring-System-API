package agh.edu.pl.healthmonitoringsystem.persistence.model.entity;

import agh.edu.pl.healthmonitoringsystem.domain.exception.RequestValidationException;
import agh.edu.pl.healthmonitoringsystem.domain.model.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static agh.edu.pl.healthmonitoringsystem.domain.validator.PreconditionValidator.checkLength;
import static agh.edu.pl.healthmonitoringsystem.domain.validator.PreconditionValidator.checkNotNull;

@Setter
@Getter
@Table(name = "user")
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String name;
    private String surname;
    private String email;
    private String pesel;
    private String pwz;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public UserEntity() {}

    @lombok.Builder(builderClassName = "Builder")
    public UserEntity(Long id, Role role, String name, String surname, String email, String pesel, String pwz,
                        LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.pesel = pesel;
        this.pwz = pwz;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static final class Builder {
        public UserEntity build(){
            checkNotNull(name, () -> new RequestValidationException("Name cannot be null"));
            checkNotNull(role, () -> new RequestValidationException("Role cannot be null"));
            checkNotNull(surname, () -> new RequestValidationException("Surname cannot be null"));
            checkNotNull(email, () -> new RequestValidationException("Email cannot be null"));
            checkNotNull(pesel, () -> new RequestValidationException("PESEL cannot be null"));
            checkLength(pesel, 11, () -> new RequestValidationException("PESEL must be 11 characters"));
            checkNotNull(createdDate, () -> new RequestValidationException("Creation date cannot be null"));
            checkNotNull(modifiedDate, () -> new RequestValidationException("Modification date cannot be null"));
            return new UserEntity(id, role, name, surname, email, pesel, pwz, createdDate, modifiedDate);
        }
    }
}
