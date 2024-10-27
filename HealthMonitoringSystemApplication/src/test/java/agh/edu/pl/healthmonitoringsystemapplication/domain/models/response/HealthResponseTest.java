package agh.edu.pl.healthmonitoringsystemapplication.domain.models.response;

import agh.edu.pl.healthmonitoringsystemapplication.ModelResponseTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HealthResponseTest {

    @Test
    public void shouldInitializeFieldsCorrectly() {
        // Given
        Long id = 1L;
        Long patientId = 2L;
        DoctorResponse doctor = ModelResponseTestUtil.doctorResponseBuilder()
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
        HealthResponse healthResponse = ModelResponseTestUtil.healthResponseBuilder()
                .id(id)
                .patientId(patientId)
                .doctor(doctor)
                .modifiedDate(modifiedDate)
                .content(content)
                .build();

        // Then
        assertEquals(id, healthResponse.getId());
        assertEquals(patientId, healthResponse.getPatientId());
        assertEquals(doctor, healthResponse.getDoctor());
        assertEquals(modifiedDate, healthResponse.getModifiedDate());
        assertEquals(content, healthResponse.getContent());
    }

    @Test
    public void builderShouldThrowExceptionWhenIdIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelResponseTestUtil.healthResponseBuilder()
                    .id(null)
                    .patientId(1L)
                    .doctor(ModelResponseTestUtil.doctorResponseBuilder().build())
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
            ModelResponseTestUtil.healthResponseBuilder()
                    .patientId(null)
                    .id(1L)
                    .doctor(ModelResponseTestUtil.doctorResponseBuilder().build())
                    .modifiedDate(LocalDateTime.now())
                    .content("Content")
                    .build();
        });
        assertEquals("Patient id cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenDoctorIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelResponseTestUtil.healthResponseBuilder()
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
            ModelResponseTestUtil.healthResponseBuilder()
                    .modifiedDate(null)
                    .id(1L)
                    .patientId(1L)
                    .doctor(ModelResponseTestUtil.doctorResponseBuilder().build())
                    .content("Content")
                    .build();
        });
        assertEquals("Modification date cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenContentIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelResponseTestUtil.healthResponseBuilder()
                    .content(null)
                    .id(1L)
                    .patientId(1L)
                    .doctor(ModelResponseTestUtil.doctorResponseBuilder().build())
                    .modifiedDate(LocalDateTime.now())
                    .build();
        });
        assertEquals("Content cannot be null", exception.getMessage());
    }
}

