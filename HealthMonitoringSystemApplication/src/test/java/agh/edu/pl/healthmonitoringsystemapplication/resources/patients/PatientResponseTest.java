package agh.edu.pl.healthmonitoringsystemapplication.resources.patients;

import agh.edu.pl.healthmonitoringsystemapplication.ModelResponseTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.exceptions.RequestValidationException;
import agh.edu.pl.healthmonitoringsystemapplication.resources.patients.models.PatientResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PatientResponseTest {

    @Test
    public void shouldInitializeFieldsCorrectly() {
        // Given
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        String email = "john.doe@example.com";
        String pesel = "12345678901";

        // When
        PatientResponse patientResponse = ModelResponseTestUtil.patientResponseBuilder()
                .id(id)
                .name(name)
                .surname(surname)
                .email(email)
                .pesel(pesel)
                .build();

        // Then
        assertEquals(id, patientResponse.getId());
        assertEquals(name, patientResponse.getName());
        assertEquals(surname, patientResponse.getSurname());
        assertEquals(email, patientResponse.getEmail());
        assertEquals(pesel, patientResponse.getPesel());
    }

    @Test
    public void builderShouldThrowExceptionWhenIdIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelResponseTestUtil.patientResponseBuilder()
                    .id(null)
                    .build();
        });
        assertEquals("Id cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenNameIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelResponseTestUtil.patientResponseBuilder()
                    .name(null)
                    .build();
        });
        assertEquals("Name cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenSurnameIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelResponseTestUtil.patientResponseBuilder()
                    .surname(null)
                    .build();
        });
        assertEquals("Surname cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenEmailIsNull() {

        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelResponseTestUtil.patientResponseBuilder()
                    .email(null)
                    .build();
        });
        assertEquals("Email cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenPeselIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelResponseTestUtil.patientResponseBuilder()
                    .pesel(null)
                    .build();
        });
        assertEquals("PESEL cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenPeselHasWrongLength() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelResponseTestUtil.patientResponseBuilder()
                    .pesel("2137")
                    .build();
        });
        assertEquals("PESEL must be 11 characters", exception.getMessage());
    }
}
