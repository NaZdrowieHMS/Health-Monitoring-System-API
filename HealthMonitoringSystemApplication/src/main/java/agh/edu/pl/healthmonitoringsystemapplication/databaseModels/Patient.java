package agh.edu.pl.healthmonitoringsystemapplication.databaseModels;

import agh.edu.pl.healthmonitoringsystemapplication.exceptions.RequestValidationException;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Patient {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String pesel;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;

    public Patient() {}

    public Patient(Long id, String name, String surname, String email, String pesel, LocalDateTime createdAt, LocalDateTime lastUpdated) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.pesel = pesel;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
    }

    // Możesz dodać metody walidacyjne tutaj, jeśli to potrzebne
}
