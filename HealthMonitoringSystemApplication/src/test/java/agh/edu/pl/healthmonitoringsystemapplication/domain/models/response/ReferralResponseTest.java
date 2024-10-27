package agh.edu.pl.healthmonitoringsystemapplication.domain.models.response;

import agh.edu.pl.healthmonitoringsystemapplication.ModelResponseTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReferralResponseTest {

    @Test
    public void shouldInitializeFieldsCorrectly() {
        // Given
        Long referralId = 1L;
        Long commentId = 2L;
        Long doctorId = 3L;
        Long patientId = 4L;
        String testType = "Blood Test";
        String referralNumber = "REF123";
        Boolean completed = true;
        String commentContent = "This is a comment.";
        LocalDateTime modifiedDate = LocalDateTime.now();

        // When
        ReferralResponse referralResponse = ModelResponseTestUtil.referralResponseBuilder()
                .referralId(referralId)
                .commentId(commentId)
                .doctorId(doctorId)
                .patientId(patientId)
                .testType(testType)
                .referralNumber(referralNumber)
                .completed(completed)
                .commentContent(commentContent)
                .modifiedDate(modifiedDate)
                .build();

        // Then
        assertEquals(referralId, referralResponse.getReferralId());
        assertEquals(commentId, referralResponse.getCommentId());
        assertEquals(doctorId, referralResponse.getDoctorId());
        assertEquals(patientId, referralResponse.getPatientId());
        assertEquals(testType, referralResponse.getTestType());
        assertEquals(referralNumber, referralResponse.getReferralNumber());
        assertEquals(completed, referralResponse.getCompleted());
        assertEquals(commentContent, referralResponse.getCommentContent());
        assertEquals(modifiedDate, referralResponse.getModifiedDate());
    }

    @Test
    public void builderShouldThrowExceptionWhenReferralIdIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelResponseTestUtil.referralResponseBuilder()
                    .referralId(null)
                    .doctorId(1L)
                    .patientId(1L)
                    .testType("Test")
                    .referralNumber("123")
                    .completed(true)
                    .modifiedDate(LocalDateTime.now())
                    .build();
        });
        assertEquals("Referral Id cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenDoctorIdIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelResponseTestUtil.referralResponseBuilder()
                    .doctorId(null)
                    .referralId(1L)
                    .patientId(1L)
                    .testType("Test")
                    .referralNumber("123")
                    .completed(true)
                    .modifiedDate(LocalDateTime.now())
                    .build();
        });
        assertEquals("Doctor id cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenPatientIdIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelResponseTestUtil.referralResponseBuilder()
                    .patientId(null)
                    .referralId(1L)
                    .doctorId(1L)
                    .testType("Test")
                    .referralNumber("123")
                    .completed(true)
                    .modifiedDate(LocalDateTime.now())
                    .build();
        });
        assertEquals("Patient id cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenTestTypeIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelResponseTestUtil.referralResponseBuilder()
                    .testType(null)
                    .referralId(1L)
                    .doctorId(1L)
                    .patientId(1L)
                    .referralNumber("123")
                    .completed(true)
                    .modifiedDate(LocalDateTime.now())
                    .build();
        });
        assertEquals("Test type cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenReferralNumberIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelResponseTestUtil.referralResponseBuilder()
                    .referralNumber(null)
                    .referralId(1L)
                    .doctorId(1L)
                    .patientId(1L)
                    .testType("Test")
                    .completed(true)
                    .modifiedDate(LocalDateTime.now())
                    .build();
        });
        assertEquals("Referral Number cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenCompletedIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelResponseTestUtil.referralResponseBuilder()
                    .completed(null)
                    .referralId(1L)
                    .doctorId(1L)
                    .patientId(1L)
                    .testType("Test")
                    .referralNumber("123")
                    .modifiedDate(LocalDateTime.now())
                    .build();
        });
        assertEquals("Completed cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenModifiedDateIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelResponseTestUtil.referralResponseBuilder()
                    .modifiedDate(null)
                    .referralId(1L)
                    .doctorId(1L)
                    .patientId(1L)
                    .testType("Test")
                    .referralNumber("123")
                    .completed(true)
                    .build();
        });
        assertEquals("Modification date cannot be null", exception.getMessage());
    }
}

