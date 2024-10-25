package agh.edu.pl.healthmonitoringsystemapplication.persistence.model;

import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {

    @Test
    public void shouldInitializeFieldsCorrectlyUsingBuilder() {
        // Given
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        String email = "john.doe@example.com";
        String pesel = "12345678901";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime lastUpdated = LocalDateTime.now();

        // When
        Patient patient = ModelTestUtil.patientBuilder()
                .id(id)
                .name(name)
                .surname(surname)
                .email(email)
                .pesel(pesel)
                .createdAt(createdAt)
                .lastUpdated(lastUpdated)
                .build();

        // Then
        assertEquals(id, patient.getId());
        assertEquals(name, patient.getName());
        assertEquals(surname, patient.getSurname());
        assertEquals(email, patient.getEmail());
        assertEquals(pesel, patient.getPesel());
        assertEquals(createdAt, patient.getCreatedAt());
        assertEquals(lastUpdated, patient.getLastUpdated());
    }

    @Test
    public void shouldInitializeFieldsCorrectlyUsingConstructor() {
        // Given
        Long id = 1L;
        String name = "Jane";
        String surname = "Smith";
        String email = "jane.smith@example.com";
        String pesel = "98765432101";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime lastUpdated = LocalDateTime.now();

        // When
        Patient patient = new Patient(id, name, surname, email, pesel, createdAt, lastUpdated);

        // Then
        assertEquals(id, patient.getId());
        assertEquals(name, patient.getName());
        assertEquals(surname, patient.getSurname());
        assertEquals(email, patient.getEmail());
        assertEquals(pesel, patient.getPesel());
        assertEquals(createdAt, patient.getCreatedAt());
        assertEquals(lastUpdated, patient.getLastUpdated());
    }

    @Test
    public void shouldCreatePatientWithDefaultConstructor() {
        // When
        Patient patient = new Patient();

        // Then
        assertNull(patient.getId());
        assertNull(patient.getName());
        assertNull(patient.getSurname());
        assertNull(patient.getEmail());
        assertNull(patient.getPesel());
        assertNull(patient.getCreatedAt());
        assertNull(patient.getLastUpdated());
    }

    @Test
    public void shouldThrowExceptionWhenNameIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.patientBuilder().name(null).build()
        );
        assertEquals("Name cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenSurnameIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.patientBuilder().surname(null).build()
        );
        assertEquals("Surname cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenEmailIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.patientBuilder().email(null).build()
        );
        assertEquals("Email cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenPeselIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.patientBuilder().pesel(null).build()
        );
        assertEquals("PESEL cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenPeselLengthIsInvalid() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.patientBuilder().pesel("2137").build()
        );
        assertEquals("PESEL must be 11 characters", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenCreatedAtIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.patientBuilder().createdAt(null).build()
        );
        assertEquals("Creation date cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenLastUpdatedIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.patientBuilder().lastUpdated(null).build()
        );
        assertEquals("Last update date cannot be null", exception.getMessage());
    }
}
