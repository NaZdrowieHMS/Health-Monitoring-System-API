package agh.edu.pl.healthmonitoringsystem.domain.models.response;

import agh.edu.pl.healthmonitoringsystem.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystem.domain.exceptions.RequestValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class DoctorTest {

    @Test
    public void shouldInitializeFieldsCorrectly() {
        // Given
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        String email = "john.doe@example.com";
        String pesel = "12345678901";
        String pwz = "5425740";

        // When
        Doctor patientResponse = ModelTestUtil.doctorBuilder()
                .id(id)
                .name(name)
                .surname(surname)
                .email(email)
                .pesel(pesel)
                .pwz(pwz)
                .build();

        // Then
        assertEquals(id, patientResponse.getId());
        assertEquals(name, patientResponse.getName());
        assertEquals(surname, patientResponse.getSurname());
        assertEquals(email, patientResponse.getEmail());
        assertEquals(pesel, patientResponse.getPesel());
        assertEquals(pwz, patientResponse.getPwz());
    }

    @Test
    public void builderShouldThrowExceptionWhenIdIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.doctorBuilder()
                    .id(null)
                    .build();
        });
        assertEquals("Id cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenNameIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.doctorBuilder()
                    .name(null)
                    .build();
        });
        assertEquals("Name cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenSurnameIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.doctorBuilder()
                    .surname(null)
                    .build();
        });
        assertEquals("Surname cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenEmailIsNull() {

        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.doctorBuilder()
                    .email(null)
                    .build();
        });
        assertEquals("Email cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenPeselIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.doctorBuilder()
                    .pesel(null)
                    .build();
        });
        assertEquals("PESEL cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenPeselHasWrongLength() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.doctorBuilder()
                    .pesel("2137")
                    .build();
        });
        assertEquals("PESEL must be 11 characters", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenPwzNumberIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.doctorBuilder()
                    .pwz(null)
                    .build();
        });
        assertEquals("PWZ number cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenPwzNumberHasWrongLength() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.doctorBuilder()
                    .pwz("11")
                    .build();
        });
        assertEquals("PWZ number must be 7 characters", exception.getMessage());
    }
}
