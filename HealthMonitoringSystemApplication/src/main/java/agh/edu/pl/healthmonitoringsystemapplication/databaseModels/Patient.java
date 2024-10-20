package agh.edu.pl.healthmonitoringsystemapplication.databaseModels;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("patient")
@Getter
@Builder
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

    public Patient(Long id, String name, String surname, String email, String pesel, LocalDateTime createdAt, LocalDateTime lastUpdated) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.pesel = pesel;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
    }
}
