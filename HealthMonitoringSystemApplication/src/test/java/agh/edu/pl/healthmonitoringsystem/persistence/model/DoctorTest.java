package agh.edu.pl.healthmonitoringsystem.persistence.model;

import agh.edu.pl.healthmonitoringsystem.ModelEntityTestUtil;
import agh.edu.pl.healthmonitoringsystem.domain.exceptions.RequestValidationException;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.DoctorEntity;
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
        LocalDateTime createdDate = LocalDateTime.of(2024, 10, 20, 10, 0);
        LocalDateTime modifiedDate = LocalDateTime.of(2024, 10, 20, 10, 0);

        // When
        DoctorEntity doctor = ModelEntityTestUtil.doctorBuilder()
                .id(id)
                .name(name)
                .surname(surname)
                .email(email)
                .pesel(pesel)
                .pwz(pwz)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .build();

        // Then
        assertEquals(id, doctor.getId());
        assertEquals(name, doctor.getName());
        assertEquals(surname, doctor.getSurname());
        assertEquals(email, doctor.getEmail());
        assertEquals(pesel, doctor.getPesel());
        assertEquals(pwz, doctor.getPwz());
        assertEquals(createdDate, doctor.getCreatedDate());
        assertEquals(modifiedDate, doctor.getModifiedDate());
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
        LocalDateTime date = LocalDateTime.of(2024, 10, 20, 10, 0);

        // When
        DoctorEntity doctor = new DoctorEntity(id, name, surname, email, pesel, pwz, date, date);

        // Then
        assertEquals(id, doctor.getId());
        assertEquals(name, doctor.getName());
        assertEquals(surname, doctor.getSurname());
        assertEquals(email, doctor.getEmail());
        assertEquals(pesel, doctor.getPesel());
        assertEquals(pwz, doctor.getPwz());
        assertEquals(date, doctor.getCreatedDate());
        assertEquals(date, doctor.getModifiedDate());
    }

    @Test
    public void shouldCreateDoctorWithDefaultConstructor() {
        // When
        DoctorEntity doctor = new DoctorEntity();

        // Then
        assertNull(doctor.getId());
        assertNull(doctor.getName());
        assertNull(doctor.getSurname());
        assertNull(doctor.getEmail());
        assertNull(doctor.getPesel());
        assertNull(doctor.getCreatedDate());
        assertNull(doctor.getPwz());
    }

    @Test
    public void shouldThrowExceptionWhenNameIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.doctorBuilder().name(null).build()
        );
        assertEquals("Name cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenSurnameIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.doctorBuilder().surname(null).build()
        );
        assertEquals("Surname cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenEmailIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.doctorBuilder().email(null).build()
        );
        assertEquals("Email cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenPeselIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.doctorBuilder().pesel(null).build()
        );
        assertEquals("PESEL cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenPwzIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.doctorBuilder().pwz(null).build()
        );
        assertEquals("PWZ number cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenPeselLengthIsInvalid() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.doctorBuilder().pesel("2137").build()
        );
        assertEquals("PESEL must be 11 characters", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenPwzLengthIsInvalid() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.doctorBuilder().pwz("123").build()
        );
        assertEquals("PWZ number must be 7 characters", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenCreatedDateAtIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.doctorBuilder().createdDate(null).build()
        );
        assertEquals("Creation date cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenModificationDateIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.doctorBuilder().modifiedDate(null).build()
        );
        assertEquals("Modification date cannot be null", exception.getMessage());
    }
}
