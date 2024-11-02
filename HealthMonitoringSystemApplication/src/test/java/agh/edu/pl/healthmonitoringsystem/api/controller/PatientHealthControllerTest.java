package agh.edu.pl.healthmonitoringsystem.api.controller;

import agh.edu.pl.healthmonitoringsystem.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.HealthComment;
import agh.edu.pl.healthmonitoringsystem.domain.service.PatientHealthService;
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

        HealthComment healthComment1 = ModelTestUtil.healthCommentBuilder()
                .id(1L)
                .patientId(patientId)
                .content("First healthComment")
                .modifiedDate(LocalDateTime.now())
                .doctor(ModelTestUtil.doctorBuilder().id(1L).name("Anna").surname("Nowak").build())
                .build();

        HealthComment healthComment2 = ModelTestUtil.healthCommentBuilder()
                .id(2L)
                .patientId(patientId)
                .content("Second healthComment")
                .modifiedDate(LocalDateTime.now())
                .doctor(ModelTestUtil.doctorBuilder().id(2L).name("Jan").surname("Kowalski").build())
                .build();

        List<HealthComment> healthComments = Arrays.asList(healthComment1, healthComment2);
        when(patientHealthService.getHealthCommentsByPatientId(patientId, startIndex, pageSize)).thenReturn(healthComments);

        // When
        ResponseEntity<List<HealthComment>> response = patientHealthController.getPatientHealthComments(startIndex, pageSize, patientId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).getContent()).isEqualTo("First healthComment");
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
        ResponseEntity<List<HealthComment>> response = patientHealthController.getPatientHealthComments(startIndex, pageSize, patientId);

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
