package agh.edu.pl.healthmonitoringsystemapplication.domain.models.response;

import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ResultTest {

    @Test
    public void shouldInitializeFieldsCorrectly() {
        // Given
        Long id = 1L;
        Long patientId = 2L;
        String testType = "Blood Test";
        ResultDataContent content = ModelTestUtil.resultDataContentBuilder().build();
        LocalDateTime createdDate = LocalDateTime.now();

        // When
        Result result = ModelTestUtil.resultBuilder()
                .id(id)
                .patientId(patientId)
                .testType(testType)
                .content(content)
                .createdDate(createdDate)
                .build();

        // Then
        assertEquals(id, result.getId());
        assertEquals(patientId, result.getPatientId());
        assertEquals(testType, result.getTestType());
        assertEquals(content, result.getContent());
        assertEquals(createdDate, result.getCreatedDate());
    }

    @Test
    public void builderShouldThrowExceptionWhenIdIsNull() {
        // Given
        Long patientId = 2L;
        String testType = "Blood Test";
        ResultDataContent content = ModelTestUtil.resultDataContentBuilder().data("Some data").type("Blood").build();
        LocalDateTime createdDate = LocalDateTime.now();

        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            Result.builder()
                    .id(null)
                    .patientId(patientId)
                    .testType(testType)
                    .content(content)
                    .createdDate(createdDate)
                    .build();
        });
        assertEquals("Id cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenPatientIdIsNull() {
        // Given
        Long id = 1L;
        String testType = "Blood Test";
        ResultDataContent content = ModelTestUtil.resultDataContentBuilder().data("Some data").type("Blood").build();
        LocalDateTime createdDate = LocalDateTime.now();

        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            Result.builder()
                    .id(id)
                    .patientId(null)
                    .testType(testType)
                    .content(content)
                    .createdDate(createdDate)
                    .build();
        });
        assertEquals("PatientEntity id cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenTestTypeIsNull() {
        // Given
        Long id = 1L;
        Long patientId = 2L;
        ResultDataContent content = ModelTestUtil.resultDataContentBuilder().data("Some data").type("Blood").build();
        LocalDateTime createdDate = LocalDateTime.now();

        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            Result.builder()
                    .id(id)
                    .patientId(patientId)
                    .testType(null)
                    .content(content)
                    .createdDate(createdDate)
                    .build();
        });
        assertEquals("Test type cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenContentIsNull() {
        // Given
        Long id = 1L;
        Long patientId = 2L;
        String testType = "Blood Test";
        LocalDateTime createdDate = LocalDateTime.now();

        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            Result.builder()
                    .id(id)
                    .patientId(patientId)
                    .testType(testType)
                    .content(null)
                    .createdDate(createdDate)
                    .build();
        });
        assertEquals("Content cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenCreatedDateIsNull() {
        // Given
        Long id = 1L;
        Long patientId = 2L;
        String testType = "Blood Test";
        ResultDataContent content = ModelTestUtil.resultDataContentBuilder().data("Some data").type("Blood").build();

        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            Result.builder()
                    .id(id)
                    .patientId(patientId)
                    .testType(testType)
                    .content(content)
                    .createdDate(null)
                    .build();
        });
        assertEquals("Creation date cannot be null", exception.getMessage());
    }
}

