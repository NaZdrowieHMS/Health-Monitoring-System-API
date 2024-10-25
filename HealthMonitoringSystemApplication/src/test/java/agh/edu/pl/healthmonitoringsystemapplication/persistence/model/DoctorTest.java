package agh.edu.pl.healthmonitoringsystemapplication.persistence.model;

import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class DoctorTest {

    @Test
    public void shouldInitializeFieldsCorrectlyUsingBuilder() {
        // Given
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        String email = "john.doe@example.com";
        String pesel = "12345678901";
        String pwz = "5425740";
        LocalDateTime createdAt = LocalDateTime.of(2024, 10, 20, 10, 0);

        // When
        Doctor doctor = ModelTestUtil.doctorBuilder()
                .id(id)
                .name(name)
                .surname(surname)
                .email(email)
                .pesel(pesel)
                .pwz(pwz)
                .createdAt(createdAt)
                .build();

        // Then
        assertEquals(id, doctor.getId());
        assertEquals(name, doctor.getName());
        assertEquals(surname, doctor.getSurname());
        assertEquals(email, doctor.getEmail());
        assertEquals(pesel, doctor.getPesel());
        assertEquals(pwz, doctor.getPwz());
        assertEquals(createdAt, doctor.getCreatedAt());
    }

    @Test
    public void shouldInitializeFieldsCorrectlyUsingConstructor() {
        // Given
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        String email = "john.doe@example.com";
        String pesel = "12345678901";
        String pwz = "5425740";
        LocalDateTime createdAt = LocalDateTime.of(2024, 10, 20, 10, 0);

        // When
        Doctor doctor = new Doctor(id, name, surname, email, pesel, pwz, createdAt);

        // Then
        assertEquals(id, doctor.getId());
        assertEquals(name, doctor.getName());
        assertEquals(surname, doctor.getSurname());
        assertEquals(email, doctor.getEmail());
        assertEquals(pesel, doctor.getPesel());
        assertEquals(pwz, doctor.getPwz());
        assertEquals(createdAt, doctor.getCreatedAt());
    }

    @Test
    public void shouldCreateDoctorWithDefaultConstructor() {
        // When
        Doctor doctor = new Doctor();

        // Then
        assertNull(doctor.getId());
        assertNull(doctor.getName());
        assertNull(doctor.getSurname());
        assertNull(doctor.getEmail());
        assertNull(doctor.getPesel());
        assertNull(doctor.getCreatedAt());
        assertNull(doctor.getPwz());
    }

    @Test
    public void shouldThrowExceptionWhenNameIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.doctorBuilder().name(null).build()
        );
        assertEquals("Name cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenSurnameIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.doctorBuilder().surname(null).build()
        );
        assertEquals("Surname cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenEmailIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.doctorBuilder().email(null).build()
        );
        assertEquals("Email cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenPeselIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.doctorBuilder().pesel(null).build()
        );
        assertEquals("PESEL cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenPwzIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.doctorBuilder().pwz(null).build()
        );
        assertEquals("PWZ number cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenPeselLengthIsInvalid() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.doctorBuilder().pesel("2137").build()
        );
        assertEquals("PESEL must be 11 characters", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenPwzLengthIsInvalid() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.doctorBuilder().pwz("123").build()
        );
        assertEquals("PWZ number must be 7 characters", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenCreatedAtIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.doctorBuilder().createdAt(null).build()
        );
        assertEquals("Creation date cannot be null", exception.getMessage());
    }
}
