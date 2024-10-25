package agh.edu.pl.healthmonitoringsystemapplication.api.resources;

import agh.edu.pl.healthmonitoringsystemapplication.ModelRequestTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.Patient;
import agh.edu.pl.healthmonitoringsystemapplication.domain.models.request.PatientRequest;
import agh.edu.pl.healthmonitoringsystemapplication.domain.models.response.PatientResponse;
import agh.edu.pl.healthmonitoringsystemapplication.domain.services.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatientControllerTest {

    @InjectMocks
    private PatientController patientController;

    @Mock
    private PatientService patientService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetPatientsShouldReturnListOfPatients() {
        // Given
        Patient patient1 = ModelTestUtil.patientBuilder().name("John").build();
        Patient patient2 = ModelTestUtil.patientBuilder().name("Jane").build();
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
        Patient patient = ModelTestUtil.patientBuilder().name("John").build();

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

    @Test
    void testCreatePatientShouldReturn201WhenSuccessful() {
        // Given
        PatientRequest patientRequest = ModelRequestTestUtil.patientRequestBuilder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("12345678901")
                .build();

        Patient createdPatient = ModelTestUtil.patientBuilder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("12345678901")
                .build();

        when(patientService.createPatient(patientRequest)).thenReturn(createdPatient);

        // When
        ResponseEntity<PatientResponse> response = patientController.createPatient(patientRequest);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getName()).isEqualTo("John");
        assertThat(response.getBody().getSurname()).isEqualTo("Doe");
        assertThat(response.getBody().getEmail()).isEqualTo("john.doe@example.com");
        assertThat(response.getBody().getPesel()).isEqualTo("12345678901");

        verify(patientService, times(1)).createPatient(patientRequest);
    }

    @Test
    void testCreatePatientShouldReturn400WhenInvalidRequest() throws Exception {
        // Given
        PatientRequest invalidRequest = ModelRequestTestUtil.patientRequestBuilder()
                .name(null)
                .build();

        // When
        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                // Then
                .andExpect(status().isBadRequest());
        verify(patientService, never()).createPatient(any(PatientRequest.class));
    }


    @Test
    void testCreatePatientShouldReturn500WhenServiceThrowsException() {
        // Given
        PatientRequest patientRequest = ModelRequestTestUtil.patientRequestBuilder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("12345678901")
                .build();

        when(patientService.createPatient(patientRequest)).thenThrow(new RuntimeException("Service error"));

        // When
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientController.createPatient(patientRequest);
        });

        // Then
        assertThat(exception.getMessage()).isEqualTo("Service error");
        verify(patientService, times(1)).createPatient(patientRequest);
    }
}


