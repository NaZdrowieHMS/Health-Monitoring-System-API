package agh.edu.pl.healthmonitoringsystemapplication.domain.models.response;

import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReferralTest {

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
        Referral referral = ModelTestUtil.referralBuilder()
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
        assertEquals(referralId, referral.getReferralId());
        assertEquals(commentId, referral.getCommentId());
        assertEquals(doctorId, referral.getDoctorId());
        assertEquals(patientId, referral.getPatientId());
        assertEquals(testType, referral.getTestType());
        assertEquals(referralNumber, referral.getReferralNumber());
        assertEquals(completed, referral.getCompleted());
        assertEquals(commentContent, referral.getCommentContent());
        assertEquals(modifiedDate, referral.getModifiedDate());
    }

    @Test
    public void builderShouldThrowExceptionWhenReferralIdIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.referralBuilder()
                    .referralId(null)
                    .doctorId(1L)
                    .patientId(1L)
                    .testType("Test")
                    .referralNumber("123")
                    .completed(true)
                    .modifiedDate(LocalDateTime.now())
                    .build();
        });
        assertEquals("ReferralEntity Id cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenDoctorIdIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.referralBuilder()
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
            ModelTestUtil.referralBuilder()
                    .patientId(null)
                    .referralId(1L)
                    .doctorId(1L)
                    .testType("Test")
                    .referralNumber("123")
                    .completed(true)
                    .modifiedDate(LocalDateTime.now())
                    .build();
        });
        assertEquals("PatientEntity id cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenTestTypeIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.referralBuilder()
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
            ModelTestUtil.referralBuilder()
                    .referralNumber(null)
                    .referralId(1L)
                    .doctorId(1L)
                    .patientId(1L)
                    .testType("Test")
                    .completed(true)
                    .modifiedDate(LocalDateTime.now())
                    .build();
        });
        assertEquals("ReferralEntity Number cannot be null", exception.getMessage());
    }

    @Test
    public void builderShouldThrowExceptionWhenCompletedIsNull() {
        // When & Then
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            ModelTestUtil.referralBuilder()
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
            ModelTestUtil.referralBuilder()
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

