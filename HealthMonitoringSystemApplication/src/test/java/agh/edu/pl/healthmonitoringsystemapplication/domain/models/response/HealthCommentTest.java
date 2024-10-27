package agh.edu.pl.healthmonitoringsystemapplication.domain.models.response;

import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HealthCommentTest {

    @Test
    public void shouldInitializeFieldsCorrectly() {
        // Given
        Long id = 1L;
        Long patientId = 2L;
        Doctor doctor = ModelTestUtil.doctorBuilder()
                .id(3L)
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("12345678901")
                .pwz("5425740")
                .build();
        LocalDateTime modifiedDate = LocalDateTime.now();
        String content = "Health record content";

        // When
        HealthComment healthComment = ModelTestUtil.healthCommentBuilder()
                .id(id)
                .patientId(patientId)
                .doctor(doctor)
                .modifiedDate(modifiedDate)
                .content(content)
                .build();

        // Then
        assertEquals(id, healthComment.getId());
        assertEquals(patientId, healthComment.getPatientId());
        assertEquals(doctor, healthComment.getDoctor());
        assertEquals(modifiedDate, healthComment.getModifiedDate());
        assertEquals(content, healthComment.getContent());
    }

    @Test
    public void builderShouldThrowExceptionWhenIdIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.healthCommentBuilder()
                    .id(null)
                    .patientId(1L)
                    .doctor(ModelTestUtil.doctorBuilder().build())
                    .modifiedDate(LocalDateTime.now())
                    .content("Content")
                    .build();
        });
        assertEquals("Id cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenPatientIdIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.healthCommentBuilder()
                    .patientId(null)
                    .id(1L)
                    .doctor(ModelTestUtil.doctorBuilder().build())
                    .modifiedDate(LocalDateTime.now())
                    .content("Content")
                    .build();
        });
        assertEquals("PatientEntity id cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenDoctorIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.healthCommentBuilder()
                    .doctor(null)
                    .id(1L)
                    .patientId(1L)
                    .modifiedDate(LocalDateTime.now())
                    .content("Content")
                    .build();
        });
        assertEquals("Doctor cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenModifiedDateIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.healthCommentBuilder()
                    .modifiedDate(null)
                    .id(1L)
                    .patientId(1L)
                    .doctor(ModelTestUtil.doctorBuilder().build())
                    .content("Content")
                    .build();
        });
        assertEquals("Modification date cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenContentIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.healthCommentBuilder()
                    .content(null)
                    .id(1L)
                    .patientId(1L)
                    .doctor(ModelTestUtil.doctorBuilder().build())
                    .modifiedDate(LocalDateTime.now())
                    .build();
        });
        assertEquals("Content cannot be null", exception.getMessage());
    }
}

