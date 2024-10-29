package agh.edu.pl.healthmonitoringsystem.api.resources;

import agh.edu.pl.healthmonitoringsystem.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystem.response.Result;
import agh.edu.pl.healthmonitoringsystem.domain.services.PatientResultService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.tensorflow.op.math.Mod;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PatientResultControllerTest {

    @InjectMocks
    private PatientResultController patientResultController;

    @Mock
    private PatientResultService patientResultService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPatientHealthCommentsReturnsResultList() {
        // Given
        Long patientId = 1L;
        int startIndex = 0;
        int pageSize = 2;

        Result result1 = new Result(
                1L,
                patientId,
                "Blood Test",
                ModelTestUtil.resultDataContent(),
                LocalDateTime.now()
        );

        Result result2 = new Result(
                2L,
                patientId,
                "X-Ray",
                ModelTestUtil.resultDataContent(),
                LocalDateTime.now()
        );

        List<Result> results = Arrays.asList(result1, result2);
        when(patientResultService.getPatientResultsByPatientId(patientId, startIndex, pageSize)).thenReturn(results);

        // When
        ResponseEntity<List<Result>> response = patientResultController.getPatientHealthComments(startIndex, pageSize, patientId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).id()).isEqualTo(1L);
        assertThat(response.getBody().get(0).testType()).isEqualTo("Blood Test");
        assertThat(response.getBody().get(1).id()).isEqualTo(2L);
        assertThat(response.getBody().get(1).testType()).isEqualTo("X-Ray");
        verify(patientResultService, times(1)).getPatientResultsByPatientId(patientId, startIndex, pageSize);
    }

    @Test
    void testGetPatientHealthCommentsReturnsEmptyListWhenNoResultsFound() {
        // Given
        Long patientId = 1L;
        int startIndex = 0;
        int pageSize = 2;

        when(patientResultService.getPatientResultsByPatientId(patientId, startIndex, pageSize)).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<Result>> response = patientResultController.getPatientHealthComments(startIndex, pageSize, patientId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
        verify(patientResultService, times(1)).getPatientResultsByPatientId(patientId, startIndex, pageSize);
    }

    @Test
    void testGetPatientHealthCommentsHandlesServiceException() {
        // Given
        Long patientId = 1L;
        int startIndex = 0;
        int pageSize = 50;

        when(patientResultService.getPatientResultsByPatientId(patientId, startIndex, pageSize))
                .thenThrow(new RuntimeException("Service error"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientResultController.getPatientHealthComments(startIndex, pageSize, patientId);
        });

        assertThat(exception.getMessage()).isEqualTo("Service error");
        verify(patientResultService, times(1)).getPatientResultsByPatientId(patientId, startIndex, pageSize);
    }
}
