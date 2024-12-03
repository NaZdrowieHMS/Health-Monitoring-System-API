package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.CommentRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Author;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Comment;
import agh.edu.pl.healthmonitoringsystem.domain.validator.RequestValidator;
import agh.edu.pl.healthmonitoringsystem.persistence.HealthRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.UserRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.HealthCommentEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HealthServiceTest {

    @Mock
    private HealthRepository healthRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RequestValidator validator;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private HealthService healthService;

    @Test
    void createHealthCommentShouldCreateAndReturnCommentWhenValidRequest() {
        // Given
        CommentRequest request = new CommentRequest(1L, 2L, "Health comment");
        LocalDateTime now = LocalDateTime.now();

        HealthCommentEntity entity = HealthCommentEntity.builder()
                .doctorId(1L)
                .patientId(2L)
                .content("Health comment")
                .createdDate(now)
                .modifiedDate(now)
                .build();

        Comment expectedComment = new Comment(1L, new Author(1L, "John", "Doe"), now, "Health comment");

        when(healthRepository.save(any(HealthCommentEntity.class))).thenReturn(entity);
        when(modelMapper.mapProjectionToComment(any())).thenReturn(expectedComment);

        // When
        Comment result = healthService.createHealthComment(request);

        // Then
        verify(validator).validate(1L, 2L);
        verify(healthRepository).save(any(HealthCommentEntity.class));
        assertEquals(expectedComment, result);
    }

    @Test
    void createHealthComment_ShouldThrowException_WhenValidationFails() {
        // Given
        CommentRequest request = new CommentRequest(1L, 2L, "Invalid");

        doThrow(new IllegalArgumentException("Validation failed"))
                .when(validator).validate(1L, 2L);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> healthService.createHealthComment(request));
        verifyNoInteractions(healthRepository);
    }
}
