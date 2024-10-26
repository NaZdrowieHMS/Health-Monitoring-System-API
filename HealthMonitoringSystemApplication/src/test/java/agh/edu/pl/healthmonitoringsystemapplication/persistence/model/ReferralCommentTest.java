package agh.edu.pl.healthmonitoringsystemapplication.persistence.model;

import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.table.ReferralComment;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReferralCommentTest {

    @Test
    public void shouldInitializeFieldsCorrectlyUsingBuilder() {
        // Given
        Long id = 1L;
        Long doctorId = 2L;
        String content = "Sample content";
        LocalDateTime createdDate = LocalDateTime.of(2024, 10, 20, 10, 0);
        LocalDateTime modifiedDate = LocalDateTime.of(2024, 10, 21, 10, 0);

        // When
        ReferralComment referralComment = ModelTestUtil.referralCommentBuilder()
                .id(id)
                .doctorId(doctorId)
                .content(content)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .build();

        // Then
        assertEquals(id, referralComment.getId());
        assertEquals(doctorId, referralComment.getDoctorId());
        assertEquals(content, referralComment.getContent());
        assertEquals(createdDate, referralComment.getCreatedDate());
        assertEquals(modifiedDate, referralComment.getModifiedDate());
    }

    @Test
    public void shouldInitializeFieldsCorrectlyUsingConstructor() {
        // Given
        Long id = 1L;
        Long doctorId = 2L;
        String content = "Sample content";
        LocalDateTime createdDate = LocalDateTime.of(2024, 10, 20, 10, 0);
        LocalDateTime modifiedDate = LocalDateTime.of(2024, 10, 21, 10, 0);

        // When
        ReferralComment referralComment = new ReferralComment(id, doctorId, content, createdDate, modifiedDate);

        // Then
        assertEquals(id, referralComment.getId());
        assertEquals(doctorId, referralComment.getDoctorId());
        assertEquals(content, referralComment.getContent());
        assertEquals(createdDate, referralComment.getCreatedDate());
        assertEquals(modifiedDate, referralComment.getModifiedDate());
    }

    @Test
    public void shouldCreateReferralCommentWithDefaultConstructor() {
        // When
        ReferralComment referralComment = new ReferralComment();

        // Then
        assertNull(referralComment.getId());
        assertNull(referralComment.getDoctorId());
        assertNull(referralComment.getContent());
        assertNull(referralComment.getCreatedDate());
        assertNull(referralComment.getModifiedDate());
    }

    @Test
    public void shouldThrowExceptionWhenDoctorIdIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.referralCommentBuilder()
                        .doctorId(null)
                        .content("Sample content")
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .build()
        );
        assertEquals("Doctor Id cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenContentIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.referralCommentBuilder()
                        .doctorId(1L)
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
                ModelTestUtil.referralCommentBuilder()
                        .doctorId(1L)
                        .content("Sample content")
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
                ModelTestUtil.referralCommentBuilder()
                        .doctorId(1L)
                        .content("Sample content")
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(null)
                        .build()
        );
        assertEquals("Modification date cannot be null", exception.getMessage());
    }
}
