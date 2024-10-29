package agh.edu.pl.healthmonitoringsystem.persistence.model;

import agh.edu.pl.healthmonitoringsystem.ModelEntityTestUtil;
import agh.edu.pl.healthmonitoringsystem.domain.exceptions.RequestValidationException;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.PatientEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class PatientEntityTest {

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
        PatientEntity patientEntity = ModelEntityTestUtil.patientBuilder()
                .id(id)
                .name(name)
                .surname(surname)
                .email(email)
                .pesel(pesel)
                .createdDate(createdAt)
                .modifiedDate(lastUpdated)
                .build();

        // Then
        assertEquals(id, patientEntity.getId());
        assertEquals(name, patientEntity.getName());
        assertEquals(surname, patientEntity.getSurname());
        assertEquals(email, patientEntity.getEmail());
        assertEquals(pesel, patientEntity.getPesel());
        assertEquals(createdAt, patientEntity.getCreatedDate());
        assertEquals(lastUpdated, patientEntity.getModifiedDate());
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
        PatientEntity patientEntity = new PatientEntity(id, name, surname, email, pesel, createdAt, lastUpdated);

        // Then
        assertEquals(id, patientEntity.getId());
        assertEquals(name, patientEntity.getName());
        assertEquals(surname, patientEntity.getSurname());
        assertEquals(email, patientEntity.getEmail());
        assertEquals(pesel, patientEntity.getPesel());
        assertEquals(createdAt, patientEntity.getCreatedDate());
        assertEquals(lastUpdated, patientEntity.getModifiedDate());
    }

    @Test
    public void shouldCreatePatientWithDefaultConstructor() {
        // When
        PatientEntity patientEntity = new PatientEntity();

        // Then
        assertNull(patientEntity.getId());
        assertNull(patientEntity.getName());
        assertNull(patientEntity.getSurname());
        assertNull(patientEntity.getEmail());
        assertNull(patientEntity.getPesel());
        assertNull(patientEntity.getCreatedDate());
        assertNull(patientEntity.getModifiedDate());
    }

    @Test
    public void shouldThrowExceptionWhenNameIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.patientBuilder().name(null).build()
        );
        assertEquals("Name cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenSurnameIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.patientBuilder().surname(null).build()
        );
        assertEquals("Surname cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenEmailIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.patientBuilder().email(null).build()
        );
        assertEquals("Email cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenPeselIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.patientBuilder().pesel(null).build()
        );
        assertEquals("PESEL cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenPeselLengthIsInvalid() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.patientBuilder().pesel("2137").build()
        );
        assertEquals("PESEL must be 11 characters", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenCreatedDateAtIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.patientBuilder().createdDate(null).build()
        );
        assertEquals("Creation date cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenModificationDateIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.patientBuilder().modifiedDate(null).build()
        );
        assertEquals("Modification date cannot be null", exception.getMessage());
    }
}
