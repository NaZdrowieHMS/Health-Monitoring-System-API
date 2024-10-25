package agh.edu.pl.healthmonitoringsystemapplication.persistence.model;

import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ResultCommentTest {

    @Test
    public void shouldInitializeFieldsCorrectlyUsingBuilder() {
        // Given
        Long id = 1L;
        Long resultId = 2L;
        Long doctorId = 3L;
        String content = "This is a comment on the result";
        LocalDateTime createdDate = LocalDateTime.of(2024, 10, 20, 10, 0);
        LocalDateTime modifiedDate = LocalDateTime.of(2024, 10, 21, 10, 0);

        // When
        ResultComment resultComment = ModelTestUtil.resultCommentBuilder()
                .id(id)
                .resultId(resultId)
                .doctorId(doctorId)
                .content(content)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .build();

        // Then
        assertEquals(id, resultComment.getId());
        assertEquals(resultId, resultComment.getResultId());
        assertEquals(doctorId, resultComment.getDoctorId());
        assertEquals(content, resultComment.getContent());
        assertEquals(createdDate, resultComment.getCreatedDate());
        assertEquals(modifiedDate, resultComment.getModifiedDate());
    }

    @Test
    public void shouldInitializeFieldsCorrectlyUsingConstructor() {
        // Given
        Long id = 1L;
        Long resultId = 2L;
        Long doctorId = 3L;
        String content = "This is a comment on the result";
        LocalDateTime createdDate = LocalDateTime.of(2024, 10, 20, 10, 0);
        LocalDateTime modifiedDate = LocalDateTime.of(2024, 10, 21, 10, 0);

        // When
        ResultComment resultComment = new ResultComment(id, resultId, doctorId, content, createdDate, modifiedDate);

        // Then
        assertEquals(id, resultComment.getId());
        assertEquals(resultId, resultComment.getResultId());
        assertEquals(doctorId, resultComment.getDoctorId());
        assertEquals(content, resultComment.getContent());
        assertEquals(createdDate, resultComment.getCreatedDate());
        assertEquals(modifiedDate, resultComment.getModifiedDate());
    }

    @Test
    public void shouldCreateResultCommentWithDefaultConstructor() {
        // When
        ResultComment resultComment = new ResultComment();

        // Then
        assertNull(resultComment.getId());
        assertNull(resultComment.getResultId());
        assertNull(resultComment.getDoctorId());
        assertNull(resultComment.getContent());
        assertNull(resultComment.getCreatedDate());
        assertNull(resultComment.getModifiedDate());
    }

    @Test
    public void shouldThrowExceptionWhenResultIdIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.resultCommentBuilder()
                        .resultId(null)
                        .doctorId(1L)
                        .content("Comment content")
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(LocalDateTime.now())
                        .build()
        );
        assertEquals("Result Id cannot be null", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenDoctorIdIsNull() {
        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () ->
                ModelTestUtil.resultCommentBuilder()
                        .resultId(1L)
                        .doctorId(null)
                        .content("Comment content")
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
                ModelTestUtil.resultCommentBuilder()
                        .resultId(1L)
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
                ModelTestUtil.resultCommentBuilder()
                        .resultId(1L)
                        .doctorId(1L)
                        .content("Comment content")
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
                ModelTestUtil.resultCommentBuilder()
                        .resultId(1L)
                        .doctorId(1L)
                        .content("Comment content")
                        .createdDate(LocalDateTime.now())
                        .modifiedDate(null)
                        .build()
        );
        assertEquals("Modification date cannot be null", exception.getMessage());
    }
}
