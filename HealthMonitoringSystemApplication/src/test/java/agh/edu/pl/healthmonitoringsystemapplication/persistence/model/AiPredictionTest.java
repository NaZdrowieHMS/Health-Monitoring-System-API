package agh.edu.pl.healthmonitoringsystemapplication.persistence.model;

import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AiPredictionTest {

    @Test
    public void shouldInitializeFieldsCorrectlyUsingBuilder() {
        // Given
        Long id = 1L;
        Long doctorId = 2L;
        Long patientId = 3L;
        String content = "Prediction content";
        LocalDateTime createdDate = LocalDateTime.of(2024, 10, 20, 10, 0);

        // When
        AiPrediction aiPrediction = ModelTestUtil.aiPredictionBuilder()
                .id(id)
                .doctorId(doctorId)
                .patientId(patientId)
                .content(content)
                .createdDate(createdDate)
                .build();

        // Then
        assertEquals(id, aiPrediction.getId());
        assertEquals(doctorId, aiPrediction.getDoctorId());
        assertEquals(patientId, aiPrediction.getPatientId());
        assertEquals(content, aiPrediction.getContent());
        assertEquals(createdDate, aiPrediction.getCreatedDate());
    }

    @Test
    public void shouldInitializeFieldsCorrectlyUsingConstructor() {
        // Given
        Long id = 1L;
        Long doctorId = 2L;
        Long patientId = 3L;
        String content = "Prediction content";
        LocalDateTime createdDate = LocalDateTime.of(2024, 10, 20, 10, 0);

        // When
        AiPrediction aiPrediction = new AiPrediction(id, doctorId, patientId, content, createdDate);

        // Then
        assertEquals(id, aiPrediction.getId());
        assertEquals(doctorId, aiPrediction.getDoctorId());
        assertEquals(patientId, aiPrediction.getPatientId());
        assertEquals(content, aiPrediction.getContent());
        assertEquals(createdDate, aiPrediction.getCreatedDate());
    }

    @Test
    public void shouldCreateAiPredictionWithDefaultConstructor() {
        // When
        AiPrediction aiPrediction = new AiPrediction();

        // Then
        assertNull(aiPrediction.getId());
        assertNull(aiPrediction.getDoctorId());
        assertNull(aiPrediction.getPatientId());
        assertNull(aiPrediction.getContent());
        assertNull(aiPrediction.getCreatedDate());
    }

    @Test
    public void shouldThrowExceptionWhenDoctorIdIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.aiPredictionBuilder()
                        .doctorId(null)
                        .patientId(1L)
                        .content("Some content")
                        .createdDate(LocalDateTime.now())
                        .build()
        );
        assertEquals("Doctor Id cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenPatientIdIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.aiPredictionBuilder()
                        .doctorId(1L)
                        .patientId(null)
                        .content("Some content")
                        .createdDate(LocalDateTime.now())
                        .build()
        );
        assertEquals("Patient Id cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenContentIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.aiPredictionBuilder()
                        .doctorId(1L)
                        .patientId(1L)
                        .content(null)
                        .createdDate(LocalDateTime.now())
                        .build()
        );
        assertEquals("Content cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenCreatedDateIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.aiPredictionBuilder()
                        .doctorId(1L)
                        .patientId(1L)
                        .content("Some content")
                        .createdDate(null)
                        .build()
        );
        assertEquals("Creation date cannot be null", exception.getMessage());
    }
}
