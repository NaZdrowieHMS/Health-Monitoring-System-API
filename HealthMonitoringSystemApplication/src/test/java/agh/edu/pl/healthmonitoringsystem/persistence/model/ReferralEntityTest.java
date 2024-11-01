package agh.edu.pl.healthmonitoringsystem.persistence.model;

import agh.edu.pl.healthmonitoringsystem.ModelEntityTestUtil;
import agh.edu.pl.healthmonitoringsystem.domain.exception.RequestValidationException;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ReferralEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReferralEntityTest {

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
        ReferralEntity referralEntity = ModelEntityTestUtil.referralBuilder()
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
        assertEquals(id, referralEntity.getId());
        assertEquals(doctorId, referralEntity.getDoctorId());
        assertEquals(patientId, referralEntity.getPatientId());
        assertEquals(commentId, referralEntity.getCommentId());
        assertEquals(testType, referralEntity.getTestType());
        assertEquals(number, referralEntity.getNumber());
        assertEquals(completed, referralEntity.getCompleted());
        assertEquals(completedDate, referralEntity.getCompletedDate());
        assertEquals(createdDate, referralEntity.getCreatedDate());
        assertEquals(modifiedDate, referralEntity.getModifiedDate());
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
        ReferralEntity referralEntity = new ReferralEntity(id, doctorId, patientId, commentId, testType, number, completed, completedDate, createdDate, modifiedDate);

        // Then
        assertEquals(id, referralEntity.getId());
        assertEquals(doctorId, referralEntity.getDoctorId());
        assertEquals(patientId, referralEntity.getPatientId());
        assertEquals(commentId, referralEntity.getCommentId());
        assertEquals(testType, referralEntity.getTestType());
        assertEquals(number, referralEntity.getNumber());
        assertEquals(completed, referralEntity.getCompleted());
        assertEquals(completedDate, referralEntity.getCompletedDate());
        assertEquals(createdDate, referralEntity.getCreatedDate());
        assertEquals(modifiedDate, referralEntity.getModifiedDate());
    }

    @Test
    public void shouldCreateReferralWithDefaultConstructor() {
        // When
        ReferralEntity referralEntity = new ReferralEntity();

        // Then
        assertNull(referralEntity.getId());
        assertNull(referralEntity.getDoctorId());
        assertNull(referralEntity.getPatientId());
        assertNull(referralEntity.getCommentId());
        assertNull(referralEntity.getTestType());
        assertNull(referralEntity.getNumber());
        assertFalse(referralEntity.getCompleted());
        assertNull(referralEntity.getCompletedDate());
        assertNull(referralEntity.getCreatedDate());
        assertNull(referralEntity.getModifiedDate());
    }

    @Test
    public void shouldThrowExceptionWhenDoctorIdIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.referralBuilder()
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
                ModelEntityTestUtil.referralBuilder()
                        .doctorId(1L)
                        .patientId(null)
                        .testType("Blood Test")
                        .number("12345")
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
                ModelEntityTestUtil.referralBuilder()
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
                ModelEntityTestUtil.referralBuilder()
                        .doctorId(1L)
                        .patientId(1L)
                        .testType("Blood Test")
                        .number(null)
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .build()
        );
        assertEquals("ReferralEntity number cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenCreatedDateIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.referralBuilder()
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
                ModelEntityTestUtil.referralBuilder()
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
