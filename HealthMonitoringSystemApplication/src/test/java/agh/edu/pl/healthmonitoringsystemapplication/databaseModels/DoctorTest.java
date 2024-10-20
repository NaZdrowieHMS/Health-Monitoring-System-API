package agh.edu.pl.healthmonitoringsystemapplication.databaseModels;

import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
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
        LocalDateTime createdAt = LocalDateTime.now();

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
        LocalDateTime createdAt = LocalDateTime.now();

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
}
