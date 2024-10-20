package agh.edu.pl.healthmonitoringsystemapplication.databaseModels;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("doctor")
@Getter
@Builder
public class Doctor {
    @Id
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String pesel;
    private String pwz;
    private LocalDateTime createdAt;

    public Doctor() {}

    public Doctor(Long id, String name, String surname, String email, String pesel, String pwz, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.pesel = pesel;
        this.pwz = pwz;
        this.createdAt = createdAt;
    }
}
