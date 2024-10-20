package agh.edu.pl.healthmonitoringsystemapplication.resources.patients;

import agh.edu.pl.healthmonitoringsystemapplication.exceptions.RequestValidationException;
import agh.edu.pl.healthmonitoringsystemapplication.models.Patient;
import agh.edu.pl.healthmonitoringsystemapplication.services.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PatientControllerTest {

    @InjectMocks
    private PatientController patientController;

    @Mock
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPatientsShouldReturnListOfPatients() {
        // Given
        Patient patient1 = new Patient(1L, "John", "Doe", "john.doe@example.com", "12345678901", null, null);
        Patient patient2 = new Patient(2L, "Jane", "Doe", "jane.doe@example.com", "98765432109", null, null);
        List<Patient> patients = Arrays.asList(patient1, patient2);

        when(patientService.getPatients(0, 50)).thenReturn(patients);

        // When
        ResponseEntity<List<PatientResponse>> response = patientController.getPatients(0, 50);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).getName()).isEqualTo("John");
        assertThat(response.getBody().get(1).getName()).isEqualTo("Jane");
    }

    @Test
    void testGetPatientsShouldReturnEmptyListWhenNoPatientsFound() {
        // Given
        when(patientService.getPatients(0, 50)).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<PatientResponse>> response = patientController.getPatients(0, 50);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    void testGetPatientByIdShouldReturnPatientResponse() {
        // Given
        Long patientId = 1L;
        Patient patient = new Patient(patientId, "John", "Doe", "john.doe@example.com", "12345678901", null, null);

        when(patientService.getPatientById(patientId)).thenReturn(patient);

        // When
        ResponseEntity<PatientResponse> response = patientController.getPatientById(patientId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(patientId);
        assertThat(response.getBody().getName()).isEqualTo("John");
    }

    @Test
    void testGetPatientByIdShouldReturn404WhenPatientNotFound() {
        // Given
        Long patientId = 999L;
        when(patientService.getPatientById(patientId)).thenThrow(new RequestValidationException("Patient not found"));

        // When
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            patientController.getPatientById(patientId);
        });
    }
}

