package agh.edu.pl.healthmonitoringsystem.persistence.model;

import agh.edu.pl.healthmonitoringsystem.ModelEntityTestUtil;
import agh.edu.pl.healthmonitoringsystem.domain.exception.RequestValidationException;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.AiPredictionCommentEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AiPredictionEntityCommentTest {

    @Test
    public void shouldInitializeFieldsCorrectlyUsingBuilder() {
        // Given
        Long id = 1L;
        Long doctorId = 2L;
        Long patientId = 3L;
        String content = "Comment content";
        LocalDateTime createdDate = LocalDateTime.of(2024, 10, 20, 10, 0);
        LocalDateTime modifiedDate = LocalDateTime.of(2024, 10, 21, 10, 0);

        // When
        AiPredictionCommentEntity aiPredictionCommentEntity = ModelEntityTestUtil.aiPredictionCommentBuilder()
                .id(id)
                .doctorId(doctorId)
                .patientId(patientId)
                .content(content)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .build();

        // Then
        assertEquals(id, aiPredictionCommentEntity.getId());
        assertEquals(doctorId, aiPredictionCommentEntity.getDoctorId());
        assertEquals(patientId, aiPredictionCommentEntity.getPatientId());
        assertEquals(content, aiPredictionCommentEntity.getContent());
        assertEquals(createdDate, aiPredictionCommentEntity.getCreatedDate());
        assertEquals(modifiedDate, aiPredictionCommentEntity.getModifiedDate());
    }

    @Test
    public void shouldInitializeFieldsCorrectlyUsingConstructor() {
        // Given
        Long id = 1L;
        Long doctorId = 2L;
        Long patientId = 3L;
        String content = "Comment content";
        LocalDateTime createdDate = LocalDateTime.of(2024, 10, 20, 10, 0);
        LocalDateTime modifiedDate = LocalDateTime.of(2024, 10, 21, 10, 0);

        // When
        AiPredictionCommentEntity aiPredictionCommentEntity = new AiPredictionCommentEntity(id, doctorId, patientId, content, createdDate, modifiedDate);

        // Then
        assertEquals(id, aiPredictionCommentEntity.getId());
        assertEquals(doctorId, aiPredictionCommentEntity.getDoctorId());
        assertEquals(patientId, aiPredictionCommentEntity.getPatientId());
        assertEquals(content, aiPredictionCommentEntity.getContent());
        assertEquals(createdDate, aiPredictionCommentEntity.getCreatedDate());
        assertEquals(modifiedDate, aiPredictionCommentEntity.getModifiedDate());
    }

    @Test
    public void shouldCreateAiPredictionCommentWithDefaultConstructor() {
        // When
        AiPredictionCommentEntity aiPredictionCommentEntity = new AiPredictionCommentEntity();

        // Then
        assertNull(aiPredictionCommentEntity.getId());
        assertNull(aiPredictionCommentEntity.getDoctorId());
        assertNull(aiPredictionCommentEntity.getPatientId());
        assertNull(aiPredictionCommentEntity.getContent());
        assertNull(aiPredictionCommentEntity.getCreatedDate());
        assertNull(aiPredictionCommentEntity.getModifiedDate());
    }

    @Test
    public void shouldThrowExceptionWhenDoctorIdIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.aiPredictionCommentBuilder()
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
                ModelEntityTestUtil.aiPredictionCommentBuilder()
                        .doctorId(1L)
                        .patientId(null)
                        .content("Some content")
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .build()
        );
        assertEquals("PatientEntity Id cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenContentIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.aiPredictionCommentBuilder()
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
                ModelEntityTestUtil.aiPredictionCommentBuilder()
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
                ModelEntityTestUtil.aiPredictionCommentBuilder()
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
