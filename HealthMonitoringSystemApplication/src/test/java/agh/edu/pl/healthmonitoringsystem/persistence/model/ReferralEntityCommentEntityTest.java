package agh.edu.pl.healthmonitoringsystem.persistence.model;

import agh.edu.pl.healthmonitoringsystem.ModelEntityTestUtil;
import agh.edu.pl.healthmonitoringsystem.domain.exceptions.RequestValidationException;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.ReferralCommentEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReferralEntityCommentEntityTest {

    @Test
    public void shouldInitializeFieldsCorrectlyUsingBuilder() {
        // Given
        Long id = 1L;
        Long doctorId = 2L;
        String content = "Sample content";
        LocalDateTime createdDate = LocalDateTime.of(2024, 10, 20, 10, 0);
        LocalDateTime modifiedDate = LocalDateTime.of(2024, 10, 21, 10, 0);

        // When
        ReferralCommentEntity referralCommentEntity = ModelEntityTestUtil.referralCommentBuilder()
                .id(id)
                .doctorId(doctorId)
                .content(content)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .build();

        // Then
        assertEquals(id, referralCommentEntity.getId());
        assertEquals(doctorId, referralCommentEntity.getDoctorId());
        assertEquals(content, referralCommentEntity.getContent());
        assertEquals(createdDate, referralCommentEntity.getCreatedDate());
        assertEquals(modifiedDate, referralCommentEntity.getModifiedDate());
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
        ReferralCommentEntity referralCommentEntity = new ReferralCommentEntity(id, doctorId, content, createdDate, modifiedDate);

        // Then
        assertEquals(id, referralCommentEntity.getId());
        assertEquals(doctorId, referralCommentEntity.getDoctorId());
        assertEquals(content, referralCommentEntity.getContent());
        assertEquals(createdDate, referralCommentEntity.getCreatedDate());
        assertEquals(modifiedDate, referralCommentEntity.getModifiedDate());
    }

    @Test
    public void shouldCreateReferralCommentWithDefaultConstructor() {
        // When
        ReferralCommentEntity referralCommentEntity = new ReferralCommentEntity();

        // Then
        assertNull(referralCommentEntity.getId());
        assertNull(referralCommentEntity.getDoctorId());
        assertNull(referralCommentEntity.getContent());
        assertNull(referralCommentEntity.getCreatedDate());
        assertNull(referralCommentEntity.getModifiedDate());
    }

    @Test
    public void shouldThrowExceptionWhenDoctorIdIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelEntityTestUtil.referralCommentBuilder()
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
                ModelEntityTestUtil.referralCommentBuilder()
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
                ModelEntityTestUtil.referralCommentBuilder()
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
                ModelEntityTestUtil.referralCommentBuilder()
                        .doctorId(1L)
                        .content("Sample content")
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(null)
                        .build()
        );
        assertEquals("Modification date cannot be null", exception.getMessage());
    }
}
