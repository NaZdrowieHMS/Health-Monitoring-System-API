package agh.edu.pl.healthmonitoringsystem.domain.models.response;

import agh.edu.pl.healthmonitoringsystem.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystem.domain.exceptions.RequestValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {

    @Test
    public void shouldInitializeFieldsCorrectly() {
        // Given
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        String email = "john.doe@example.com";
        String pesel = "12345678901";

        // When
        Patient patient = ModelTestUtil.patientBuilder()
                .id(id)
                .name(name)
                .surname(surname)
                .email(email)
                .pesel(pesel)
                .build();

        // Then
        assertEquals(id, patient.getId());
        assertEquals(name, patient.getName());
        assertEquals(surname, patient.getSurname());
        assertEquals(email, patient.getEmail());
        assertEquals(pesel, patient.getPesel());
    }

    @Test
    public void builderShouldThrowExceptionWhenIdIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.patientBuilder()
                    .id(null)
                    .build();
        });
        assertEquals("Id cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenNameIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.patientBuilder()
                    .name(null)
                    .build();
        });
        assertEquals("Name cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenSurnameIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.patientBuilder()
                    .surname(null)
                    .build();
        });
        assertEquals("Surname cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenEmailIsNull() {

        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.patientBuilder()
                    .email(null)
                    .build();
        });
        assertEquals("Email cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenPeselIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.patientBuilder()
                    .pesel(null)
                    .build();
        });
        assertEquals("PESEL cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenPeselHasWrongLength() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.patientBuilder()
                    .pesel("2137")
                    .build();
        });
        assertEquals("PESEL must be 11 characters", exception.getMessage());
    }
}
