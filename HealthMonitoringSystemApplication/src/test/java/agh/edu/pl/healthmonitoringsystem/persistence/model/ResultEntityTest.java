package agh.edu.pl.healthmonitoringsystem.persistence.model;

import agh.edu.pl.healthmonitoringsystem.ModelEntityTestUtil;
import agh.edu.pl.healthmonitoringsystem.domain.exception.RequestValidationException;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ResultEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ResultEntityTest {

    @Test
    public void shouldInitializeFieldsCorrectlyUsingBuilder() {
        // Given
        Long id = 1L;
        Long patientId = 2L;
        String testType = "Blood Test";
        String content = "Test result content";
        Boolean aiSelected = false;
        Boolean viewed = true;
        LocalDateTime createdDate = LocalDateTime.of(2024, 10, 20, 10, 0);
        LocalDateTime modifiedDate = LocalDateTime.of(2024, 10, 21, 10, 0);

        // When
        ResultEntity resultEntity = ModelEntityTestUtil.resultBuilder()
                .id(id)
                .patientId(patientId)
                .testType(testType)
                .content(content)
                .aiSelected(aiSelected)
                .viewed(viewed)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .build();

        // Then
        assertEquals(id, resultEntity.getId());
        assertEquals(patientId, resultEntity.getPatientId());
        assertEquals(testType, resultEntity.getTestType());
        assertEquals(content, resultEntity.getContent());
        assertEquals(aiSelected, resultEntity.getAiSelected());
        assertEquals(viewed, resultEntity.getViewed());
        assertEquals(createdDate, resultEntity.getCreatedDate());
        assertEquals(modifiedDate, resultEntity.getModifiedDate());
    }

    @Test
    public void shouldInitializeFieldsCorrectlyUsingConstructor() {
        // Given
        Long id = 1L;
        Long patientId = 2L;
        String testType = "Blood Test";
        String content = "Test resultEntity content";
        Boolean aiSelected = false;
        Boolean viewed = true;
        LocalDateTime createdDate = LocalDateTime.of(2024, 10, 20, 10, 0);
        LocalDateTime modifiedDate = LocalDateTime.of(2024, 10, 21, 10, 0);

        // When
        ResultEntity resultEntity = new ResultEntity(id, patientId, testType, content, aiSelected, viewed, createdDate, modifiedDate);

        // Then
        assertEquals(id, resultEntity.getId());
        assertEquals(patientId, resultEntity.getPatientId());
        assertEquals(testType, resultEntity.getTestType());
        assertEquals(content, resultEntity.getContent());
        assertEquals(aiSelected, resultEntity.getAiSelected());
        assertEquals(viewed, resultEntity.getViewed());
        assertEquals(createdDate, resultEntity.getCreatedDate());
        assertEquals(modifiedDate, resultEntity.getModifiedDate());
    }

    @Test
    public void shouldCreateResultWithDefaultConstructor() {
        // When
        ResultEntity resultEntity = new ResultEntity();

        // Then
        assertNull(resultEntity.getId());
        assertNull(resultEntity.getPatientId());
        assertNull(resultEntity.getTestType());
        assertNull(resultEntity.getContent());
        assertFalse(resultEntity.getAiSelected());
        assertFalse(resultEntity.getViewed());
        assertNull(resultEntity.getCreatedDate());
        assertNull(resultEntity.getModifiedDate());
    }

    @Test
    public void shouldThrowExceptionWhenPatientIdIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.resultBuilder()
                        .patientId(null)
                        .testType("Blood Test")
                        .content("Test result content")
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .build()
        );
        assertEquals("PatientEntity Id cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenTestTypeIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.resultBuilder()
                        .patientId(1L)
                        .testType(null)
                        .content("Test result content")
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .build()
        );
        assertEquals("Test type cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenContentIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.resultBuilder()
                        .patientId(1L)
                        .testType("Blood Test")
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
                ModelEntityTestUtil.resultBuilder()
                        .patientId(1L)
                        .testType("Blood Test")
                        .content("Test result content")
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
                ModelEntityTestUtil.resultBuilder()
                        .patientId(1L)
                        .testType("Blood Test")
                        .content("Test result content")
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(null)
                        .build()
        );
        assertEquals("Modification date cannot be null", exception.getMessage());
    }
}
