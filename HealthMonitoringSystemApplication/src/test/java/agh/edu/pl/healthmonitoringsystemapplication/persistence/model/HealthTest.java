package agh.edu.pl.healthmonitoringsystemapplication.persistence.model;

import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class HealthTest {

    @Test
    public void shouldInitializeFieldsCorrectlyUsingBuilder() {
        // Given
        Long id = 1L;
        Long doctorId = 2L;
        Long patientId = 3L;
        String content = "Health content";
        LocalDateTime createdDate = LocalDateTime.of(2024, 10, 20, 10, 0);
        LocalDateTime modifiedDate = LocalDateTime.of(2024, 10, 21, 10, 0);

        // When
        Health health = ModelTestUtil.healthBuilder()
                .id(id)
                .doctorId(doctorId)
                .patientId(patientId)
                .content(content)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .build();

        // Then
        assertEquals(id, health.getId());
        assertEquals(doctorId, health.getDoctorId());
        assertEquals(patientId, health.getPatientId());
        assertEquals(content, health.getContent());
        assertEquals(createdDate, health.getCreatedDate());
        assertEquals(modifiedDate, health.getModifiedDate());
    }

    @Test
    public void shouldInitializeFieldsCorrectlyUsingConstructor() {
        // Given
        Long id = 1L;
        Long doctorId = 2L;
        Long patientId = 3L;
        String content = "Health content";
        LocalDateTime createdDate = LocalDateTime.of(2024, 10, 20, 10, 0);
        LocalDateTime modifiedDate = LocalDateTime.of(2024, 10, 21, 10, 0);

        // When
        Health health = new Health(id, doctorId, patientId, content, createdDate, modifiedDate);

        // Then
        assertEquals(id, health.getId());
        assertEquals(doctorId, health.getDoctorId());
        assertEquals(patientId, health.getPatientId());
        assertEquals(content, health.getContent());
        assertEquals(createdDate, health.getCreatedDate());
        assertEquals(modifiedDate, health.getModifiedDate());
    }

    @Test
    public void shouldCreateHealthWithDefaultConstructor() {
        // When
        Health health = new Health();

        // Then
        assertNull(health.getId());
        assertNull(health.getDoctorId());
        assertNull(health.getPatientId());
        assertNull(health.getContent());
        assertNull(health.getCreatedDate());
        assertNull(health.getModifiedDate());
    }

    @Test
    public void shouldThrowExceptionWhenDoctorIdIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.healthBuilder()
                        .doctorId(null)
                        .patientId(1L)
                        .content("Some content")
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .build()
        );
        assertEquals("Doctor Id cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenPatientIdIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.healthBuilder()
                        .doctorId(1L)
                        .patientId(null)
                        .content("Some content")
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .build()
        );
        assertEquals("Patient Id cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenContentIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.healthBuilder()
                        .doctorId(1L)
                        .patientId(1L)
                        .content(null)
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .build()
        );
        assertEquals("Content cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenCreatedDateIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.healthBuilder()
                        .doctorId(1L)
                        .patientId(1L)
                        .content("Some content")
                        .createdDate(null)
                        .modifiedDate(LocalDateTime.now())
                        .build()
        );
        assertEquals("Creation date cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenModifiedDateIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.healthBuilder()
                        .doctorId(1L)
                        .patientId(1L)
                        .content("Some content")
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(null)
                        .build()
        );
        assertEquals("Modification date cannot be null", exception.getMessage());
    }
}
