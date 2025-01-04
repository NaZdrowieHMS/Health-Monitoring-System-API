package agh.edu.pl.healthmonitoringsystem.domain.model.request;

import agh.edu.pl.healthmonitoringsystem.domain.model.Role;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
    @NotBlank(message = "Role is required")
    @Pattern(regexp = "^(Doctor|Patient)$", message = "Role must be either 'Doctor' or 'Patient'")
    private String role;

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
    private String pwz;

    @NotBlank(message = "Password is required")
    private String password;

    @JsonCreator
    public UserRequest(@JsonProperty("name") String name,
                         @JsonProperty("surname") String surname,
                         @JsonProperty("email") String email,
                         @JsonProperty("pesel") String pesel,
                         @JsonProperty("pwz") String pwz,
                       @JsonProperty("role") String role,
                       @JsonProperty("password") String password
                       ) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.pesel = pesel;
        this.pwz = pwz;
        this.role = role;
        this.password = password;
    }
}
