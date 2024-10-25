package agh.edu.pl.healthmonitoringsystemapplication.persistence.model;

import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReferralTest {

    @Test
    public void shouldInitializeFieldsCorrectlyUsingBuilder() {
        // Given
        Long id = 1L;
        Long doctorId = 2L;
        Long patientId = 3L;
        Long commentId = 4L;
        String testType = "Blood Test";
        String number = "12345";
        Boolean completed = true;
        LocalDateTime completedDate = LocalDateTime.of(2024, 10, 25, 10, 0);
        LocalDateTime createdDate = LocalDateTime.of(2024, 10, 20, 10, 0);
        LocalDateTime modifiedDate = LocalDateTime.of(2024, 10, 21, 10, 0);

        // When
        Referral referral = ModelTestUtil.referralBuilder()
                .id(id)
                .doctorId(doctorId)
                .patientId(patientId)
                .commentId(commentId)
                .testType(testType)
                .number(number)
                .completed(completed)
                .completedDate(completedDate)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .build();

        // Then
        assertEquals(id, referral.getId());
        assertEquals(doctorId, referral.getDoctorId());
        assertEquals(patientId, referral.getPatientId());
        assertEquals(commentId, referral.getCommentId());
        assertEquals(testType, referral.getTestType());
        assertEquals(number, referral.getNumber());
        assertEquals(completed, referral.getCompleted());
        assertEquals(completedDate, referral.getCompletedDate());
        assertEquals(createdDate, referral.getCreatedDate());
        assertEquals(modifiedDate, referral.getModifiedDate());
    }

    @Test
    public void shouldInitializeFieldsCorrectlyUsingConstructor() {
        // Given
        Long id = 1L;
        Long doctorId = 2L;
        Long patientId = 3L;
        Long commentId = 4L;
        String testType = "Blood Test";
        String number = "12345";
        Boolean completed = true;
        LocalDateTime completedDate = LocalDateTime.of(2024, 10, 25, 10, 0);
        LocalDateTime createdDate = LocalDateTime.of(2024, 10, 20, 10, 0);
        LocalDateTime modifiedDate = LocalDateTime.of(2024, 10, 21, 10, 0);

        // When
        Referral referral = new Referral(id, doctorId, patientId, commentId, testType, number, completed, completedDate, createdDate, modifiedDate);

        // Then
        assertEquals(id, referral.getId());
        assertEquals(doctorId, referral.getDoctorId());
        assertEquals(patientId, referral.getPatientId());
        assertEquals(commentId, referral.getCommentId());
        assertEquals(testType, referral.getTestType());
        assertEquals(number, referral.getNumber());
        assertEquals(completed, referral.getCompleted());
        assertEquals(completedDate, referral.getCompletedDate());
        assertEquals(createdDate, referral.getCreatedDate());
        assertEquals(modifiedDate, referral.getModifiedDate());
    }

    @Test
    public void shouldCreateReferralWithDefaultConstructor() {
        // When
        Referral referral = new Referral();

        // Then
        assertNull(referral.getId());
        assertNull(referral.getDoctorId());
        assertNull(referral.getPatientId());
        assertNull(referral.getCommentId());
        assertNull(referral.getTestType());
        assertNull(referral.getNumber());
        assertFalse(referral.getCompleted());
        assertNull(referral.getCompletedDate());
        assertNull(referral.getCreatedDate());
        assertNull(referral.getModifiedDate());
    }

    @Test
    public void shouldThrowExceptionWhenDoctorIdIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.referralBuilder()
                        .doctorId(null)
                        .patientId(1L)
                        .testType("Blood Test")
                        .number("12345")
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
                ModelTestUtil.referralBuilder()
                        .doctorId(1L)
                        .patientId(null)
                        .testType("Blood Test")
                        .number("12345")
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
                ModelTestUtil.referralBuilder()
                        .doctorId(1L)
                        .patientId(1L)
                        .testType(null)
                        .number("12345")
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .build()
        );
        assertEquals("Test type cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenNumberIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.referralBuilder()
                        .doctorId(1L)
                        .patientId(1L)
                        .testType("Blood Test")
                        .number(null)
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .build()
        );
        assertEquals("Referral number cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenCreatedDateIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.referralBuilder()
                        .doctorId(1L)
                        .patientId(1L)
                        .testType("Blood Test")
                        .number("12345")
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
                ModelTestUtil.referralBuilder()
                        .doctorId(1L)
                        .patientId(1L)
                        .testType("Blood Test")
                        .number("12345")
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(null)
                        .build()
        );
        assertEquals("Modification date cannot be null", exception.getMessage());
    }
}
