package agh.edu.pl.healthmonitoringsystem.persistence.model;

import agh.edu.pl.healthmonitoringsystem.ModelEntityTestUtil;
import agh.edu.pl.healthmonitoringsystem.domain.exception.RequestValidationException;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.AiPredictionEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AiPredictionEntityTest {

    @Test
    public void shouldInitializeFieldsCorrectlyUsingBuilder() {
        // Given
        Long id = 1L;
        Long doctorId = 2L;
        Long patientId = 3L;
        String content = "Prediction content";
        LocalDateTime createdDate = LocalDateTime.of(2024, 10, 20, 10, 0);

        // When
        AiPredictionEntity aiPredictionEntity = ModelEntityTestUtil.aiPredictionBuilder()
                .id(id)
                .doctorId(doctorId)
                .patientId(patientId)
                .content(content)
                .createdDate(createdDate)
                .build();

        // Then
        assertEquals(id, aiPredictionEntity.getId());
        assertEquals(doctorId, aiPredictionEntity.getDoctorId());
        assertEquals(patientId, aiPredictionEntity.getPatientId());
        assertEquals(content, aiPredictionEntity.getContent());
        assertEquals(createdDate, aiPredictionEntity.getCreatedDate());
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
        AiPredictionEntity aiPredictionEntity = new AiPredictionEntity(id, doctorId, patientId, content, createdDate);

        // Then
        assertEquals(id, aiPredictionEntity.getId());
        assertEquals(doctorId, aiPredictionEntity.getDoctorId());
        assertEquals(patientId, aiPredictionEntity.getPatientId());
        assertEquals(content, aiPredictionEntity.getContent());
        assertEquals(createdDate, aiPredictionEntity.getCreatedDate());
    }

    @Test
    public void shouldCreateAiPredictionWithDefaultConstructor() {
        // When
        AiPredictionEntity aiPredictionEntity = new AiPredictionEntity();

        // Then
        assertNull(aiPredictionEntity.getId());
        assertNull(aiPredictionEntity.getDoctorId());
        assertNull(aiPredictionEntity.getPatientId());
        assertNull(aiPredictionEntity.getContent());
        assertNull(aiPredictionEntity.getCreatedDate());
    }

    @Test
    public void shouldThrowExceptionWhenDoctorIdIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.aiPredictionBuilder()
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
                ModelEntityTestUtil.aiPredictionBuilder()
                        .doctorId(1L)
                        .patientId(null)
                        .content("Some content")
                        .createdDate(LocalDateTime.now())
                        .build()
        );
        assertEquals("PatientEntity Id cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenContentIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.aiPredictionBuilder()
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
                ModelEntityTestUtil.aiPredictionBuilder()
                        .doctorId(1L)
                        .patientId(1L)
                        .content("Some content")
                        .createdDate(null)
                        .build()
        );
        assertEquals("Creation date cannot be null", exception.getMessage());
    }
}
