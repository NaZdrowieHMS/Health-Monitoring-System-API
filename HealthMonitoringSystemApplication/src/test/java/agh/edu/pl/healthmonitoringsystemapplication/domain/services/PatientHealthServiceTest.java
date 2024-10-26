package agh.edu.pl.healthmonitoringsystemapplication.domain.services;

import agh.edu.pl.healthmonitoringsystemapplication.persistence.HealthRepository;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.projection.HealthCommentWithAuthorProjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PatientHealthServiceTest {

    @InjectMocks
    private PatientHealthService patientHealthService;

    @Mock
    private HealthRepository healthRepository;

    @Mock
    private Page<HealthCommentWithAuthorProjection> healthCommentsPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetHealthCommentsByPatientIdReturnsComments() {
        // Given
        Long patientId = 1L;
        int page = 0;
        int size = 2;

        HealthCommentWithAuthorProjection healthComment1 = mock(HealthCommentWithAuthorProjection.class);
        when(healthComment1.getHealthCommentId()).thenReturn(1L);
        when(healthComment1.getPatientId()).thenReturn(patientId);
        when(healthComment1.getContent()).thenReturn("First comment");

        HealthCommentWithAuthorProjection healthComment2 = mock(HealthCommentWithAuthorProjection.class);
        when(healthComment2.getHealthCommentId()).thenReturn(2L);
        when(healthComment2.getPatientId()).thenReturn(patientId);
        when(healthComment2.getContent()).thenReturn("Second comment");

        List<HealthCommentWithAuthorProjection> healthComments = Arrays.asList(healthComment1, healthComment2);
        when(healthRepository.getHealthCommentsWithAutorByPatientId(patientId, PageRequest.of(page, size))).thenReturn(healthCommentsPage);
        when(healthCommentsPage.getContent()).thenReturn(healthComments);

        // When
        List<HealthCommentWithAuthorProjection> result = patientHealthService.getHealthCommentsByPatientId(patientId, page, size);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getContent()).isEqualTo("First comment");
        assertThat(result.get(1).getContent()).isEqualTo("Second comment");
        verify(healthRepository, times(1)).getHealthCommentsWithAutorByPatientId(patientId, PageRequest.of(page, size));
    }

    @Test
    void testGetHealthCommentsByPatientIdReturnsEmptyListWhenNoCommentsFound() {
        // Given
        Long patientId = 1L;
        int page = 0;
        int size = 2;

        when(healthRepository.getHealthCommentsWithAutorByPatientId(patientId, PageRequest.of(page, size))).thenReturn(healthCommentsPage);
        when(healthCommentsPage.getContent()).thenReturn(Collections.emptyList());

        // When
        List<HealthCommentWithAuthorProjection> result = patientHealthService.getHealthCommentsByPatientId(patientId, page, size);

        // Then
        assertThat(result).isEmpty();
        verify(healthRepository, times(1)).getHealthCommentsWithAutorByPatientId(patientId, PageRequest.of(page, size));
    }

    @Test
    void testGetHealthCommentsByPatientIdHandlesException() {
        // Given
        Long patientId = 1L;
        int page = 0;
        int size = 2;

        when(healthRepository.getHealthCommentsWithAutorByPatientId(patientId, PageRequest.of(page, size))).thenThrow(new RuntimeException("Repository error"));

        // When & Then
        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            patientHealthService.getHealthCommentsByPatientId(patientId, page, size);
        });

        assertThat(exception.getMessage()).isEqualTo("Repository error");
        verify(healthRepository, times(1)).getHealthCommentsWithAutorByPatientId(patientId, PageRequest.of(page, size));
    }
}

