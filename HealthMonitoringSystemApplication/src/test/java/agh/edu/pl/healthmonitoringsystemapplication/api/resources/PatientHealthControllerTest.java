package agh.edu.pl.healthmonitoringsystemapplication.api.resources;

import agh.edu.pl.healthmonitoringsystemapplication.domain.models.response.HealthResponse;
import agh.edu.pl.healthmonitoringsystemapplication.domain.services.PatientHealthService;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.projection.HealthCommentWithAuthorProjection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PatientHealthControllerTest {

    @InjectMocks
    private PatientHealthController patientHealthController;

    @Mock
    private PatientHealthService patientHealthService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPatientHealthCommentsReturnsHealthCommentList() {
        // Given
        Long patientId = 1L;
        int startIndex = 0;
        int pageSize = 2;

        HealthCommentWithAuthorProjection healthComment1 = mock(HealthCommentWithAuthorProjection.class);
        when(healthComment1.getHealthCommentId()).thenReturn(1L);
        when(healthComment1.getPatientId()).thenReturn(patientId);
        when(healthComment1.getContent()).thenReturn("First comment");
        when(healthComment1.getModifiedDate()).thenReturn(LocalDateTime.now());
        when(healthComment1.getDoctorId()).thenReturn(1L);
        when(healthComment1.getDoctorName()).thenReturn("Anna");
        when(healthComment1.getDoctorSurname()).thenReturn("Nowak");
        when(healthComment1.getDoctorEmail()).thenReturn("anna.nowak@mail.com");
        when(healthComment1.getDoctorPesel()).thenReturn("12345678909");
        when(healthComment1.getPwz()).thenReturn("5425740");

        HealthCommentWithAuthorProjection healthComment2 = mock(HealthCommentWithAuthorProjection.class);
        when(healthComment2.getHealthCommentId()).thenReturn(2L);
        when(healthComment2.getPatientId()).thenReturn(patientId);
        when(healthComment2.getContent()).thenReturn("Second comment");
        when(healthComment2.getModifiedDate()).thenReturn(LocalDateTime.now());
        when(healthComment2.getDoctorId()).thenReturn(2L);
        when(healthComment2.getDoctorName()).thenReturn("Jan");
        when(healthComment2.getDoctorSurname()).thenReturn("Kowalski");
        when(healthComment2.getDoctorEmail()).thenReturn("jan.kowalski@mail.com");
        when(healthComment2.getDoctorPesel()).thenReturn("09876543211");
        when(healthComment2.getPwz()).thenReturn("7865431");

        List<HealthCommentWithAuthorProjection> healthComments = Arrays.asList(healthComment1, healthComment2);

        when(patientHealthService.getHealthCommentsByPatientId(patientId, startIndex, pageSize)).thenReturn(healthComments);

        // When
        ResponseEntity<List<HealthResponse>> response = patientHealthController.getPatientHealthComments(startIndex, pageSize, patientId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).getContent()).isEqualTo("First comment");
        verify(patientHealthService, times(1)).getHealthCommentsByPatientId(patientId, startIndex, pageSize);
    }

    @Test
    void testGetPatientHealthCommentsReturnsEmptyListWhenNoCommentsFound() {
        // Given
        Long patientId = 1L;
        int startIndex = 0;
        int pageSize = 2;

        when(patientHealthService.getHealthCommentsByPatientId(patientId, startIndex, pageSize)).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<HealthResponse>> response = patientHealthController.getPatientHealthComments(startIndex, pageSize, patientId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
        verify(patientHealthService, times(1)).getHealthCommentsByPatientId(patientId, startIndex, pageSize);
    }

    @Test
    void testGetPatientHealthCommentsHandlesServiceException() {
        // Given
        Long patientId = 1L;
        int startIndex = 0;
        int pageSize = 2;

        when(patientHealthService.getHealthCommentsByPatientId(patientId, startIndex, pageSize)).thenThrow(new RuntimeException("Service error"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientHealthController.getPatientHealthComments(startIndex, pageSize, patientId);
        });

        assertThat(exception.getMessage()).isEqualTo("Service error");
        verify(patientHealthService, times(1)).getHealthCommentsByPatientId(patientId, startIndex, pageSize);
    }
}
