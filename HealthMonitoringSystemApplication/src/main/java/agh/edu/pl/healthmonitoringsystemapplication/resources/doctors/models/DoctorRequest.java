package agh.edu.pl.healthmonitoringsystemapplication.resources.doctors.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Surname is required")
    private String surname;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(regexp = "\\d{11}", message = "Invalid PESEL format")
    @NotBlank(message = "PESEL is required")
    private String pesel;

    @Pattern(regexp = "\\d{7}", message = "Invalid PWZ format")
    @NotBlank(message = "PWZ is required")
    private String pwz;

    @JsonCreator
    public DoctorRequest(@JsonProperty("name") String name,
                         @JsonProperty("surname") String surname,
                         @JsonProperty("email") String email,
                         @JsonProperty("pesel") String pesel,
                         @JsonProperty("pwz") String pwz) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.pesel = pesel;
        this.pwz = pwz;
    }
}
