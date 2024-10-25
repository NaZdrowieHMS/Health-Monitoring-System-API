package agh.edu.pl.healthmonitoringsystemapplication.persistence.model;

import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ResultTest {

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
        Result result = ModelTestUtil.resultBuilder()
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
        assertEquals(id, result.getId());
        assertEquals(patientId, result.getPatientId());
        assertEquals(testType, result.getTestType());
        assertEquals(content, result.getContent());
        assertEquals(aiSelected, result.getAiSelected());
        assertEquals(viewed, result.getViewed());
        assertEquals(createdDate, result.getCreatedDate());
        assertEquals(modifiedDate, result.getModifiedDate());
    }

    @Test
    public void shouldInitializeFieldsCorrectlyUsingConstructor() {
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
        Result result = new Result(id, patientId, testType, content, aiSelected, viewed, createdDate, modifiedDate);

        // Then
        assertEquals(id, result.getId());
        assertEquals(patientId, result.getPatientId());
        assertEquals(testType, result.getTestType());
        assertEquals(content, result.getContent());
        assertEquals(aiSelected, result.getAiSelected());
        assertEquals(viewed, result.getViewed());
        assertEquals(createdDate, result.getCreatedDate());
        assertEquals(modifiedDate, result.getModifiedDate());
    }

    @Test
    public void shouldCreateResultWithDefaultConstructor() {
        // When
        Result result = new Result();

        // Then
        assertNull(result.getId());
        assertNull(result.getPatientId());
        assertNull(result.getTestType());
        assertNull(result.getContent());
        assertFalse(result.getAiSelected());
        assertFalse(result.getViewed());
        assertNull(result.getCreatedDate());
        assertNull(result.getModifiedDate());
    }

    @Test
    public void shouldThrowExceptionWhenPatientIdIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.resultBuilder()
                        .patientId(null)
                        .testType("Blood Test")
                        .content("Test result content")
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .build()
        );
        assertEquals("Patient Id cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenTestTypeIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.resultBuilder()
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
                ModelTestUtil.resultBuilder()
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
                ModelTestUtil.resultBuilder()
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
                ModelTestUtil.resultBuilder()
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
