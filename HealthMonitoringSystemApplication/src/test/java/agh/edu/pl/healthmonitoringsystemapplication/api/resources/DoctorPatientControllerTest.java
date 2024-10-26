package agh.edu.pl.healthmonitoringsystemapplication.api.resources;

import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.domain.models.response.PatientResponse;
import agh.edu.pl.healthmonitoringsystemapplication.domain.services.DoctorPatientService;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.table.Patient;
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

class DoctorPatientControllerTest {

    @InjectMocks
    private DoctorPatientController doctorPatientController;

    @Mock
    private DoctorPatientService doctorPatientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDoctorPatientsReturnsPatientList() {
        // Given
        Long doctorId = 1L;
        int startIndex = 0;
        int pageSize = 2;

        Patient patient1 = ModelTestUtil.patientBuilder().name("John").build();
        Patient patient2 = ModelTestUtil.patientBuilder().name("Jane").build();
        List<Patient> patients = Arrays.asList(patient1, patient2);

        when(doctorPatientService.getPatientsByDoctorId(doctorId, startIndex, pageSize)).thenReturn(patients);

        // When
        ResponseEntity<List<PatientResponse>> response = doctorPatientController.getDoctorPatients(startIndex, pageSize, doctorId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get(0).getName()).isEqualTo("John");
        verify(doctorPatientService, times(1)).getPatientsByDoctorId(doctorId, startIndex, pageSize);
    }

    @Test
    void testGetDoctorPatientsReturnsEmptyListWhenNoPatientsFound() {
        // Given
        Long doctorId = 1L;
        int startIndex = 0;
        int pageSize = 2;

        when(doctorPatientService.getPatientsByDoctorId(doctorId, startIndex, pageSize)).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<PatientResponse>> response = doctorPatientController.getDoctorPatients(startIndex, pageSize, doctorId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
        verify(doctorPatientService, times(1)).getPatientsByDoctorId(doctorId, startIndex, pageSize);
    }

    @Test
    void testGetDoctorPatientsHandlesServiceException() {
        // Given
        Long doctorId = 1L;
        int startIndex = -4;
        int pageSize = 50;

        when(doctorPatientService.getPatientsByDoctorId(doctorId, startIndex, pageSize)).thenThrow(new RuntimeException("Service error"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            doctorPatientController.getDoctorPatients(startIndex, pageSize, doctorId);
        });

        assertThat(exception.getMessage()).isEqualTo("Service error");
        verify(doctorPatientService, times(1)).getPatientsByDoctorId(doctorId, startIndex, pageSize);
    }
}
