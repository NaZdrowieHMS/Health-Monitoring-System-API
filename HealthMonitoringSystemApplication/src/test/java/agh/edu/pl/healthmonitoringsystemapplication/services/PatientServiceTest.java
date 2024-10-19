package agh.edu.pl.healthmonitoringsystemapplication.services;

import agh.edu.pl.healthmonitoringsystemapplication.models.Patient;
import agh.edu.pl.healthmonitoringsystemapplication.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private Page<Patient> patientPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPatientsShouldReturnListOfPatients() {
        // Given
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Patient patient = new Patient(1L, "John", "Doe", "john.doe@example.com", "12345678901", null, null);

        when(patientRepository.findAll(pageable)).thenReturn(patientPage);
        when(patientPage.getContent()).thenReturn(Collections.singletonList(patient));

        // When
        List<Patient> patients = patientService.getPatients(page, size);

        // Then
        assertThat(patients).hasSize(1);
        assertThat(patients.get(0).getName()).isEqualTo("John");
        verify(patientRepository, times(1)).findAll(pageable);
    }

    @Test
    void getPatientsShouldReturnEmptyListWhenNoPatientsFound() {
        // Given
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        when(patientRepository.findAll(pageable)).thenReturn(patientPage);
        when(patientPage.getContent()).thenReturn(Collections.emptyList());

        // When
        List<Patient> patients = patientService.getPatients(page, size);

        // Then
        assertThat(patients).isEmpty();
        verify(patientRepository, times(1)).findAll(pageable);
    }

    @Test
    void getPatientByIdShouldReturnPatientWhenFound() {
        // Given
        Long patientId = 1L;
        Patient patient = new Patient(patientId, "John", "Doe", "john.doe@example.com", "12345678901", null, null);

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        // When
        Patient result = patientService.getPatientById(patientId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(patientId);
        assertThat(result.getName()).isEqualTo("John");
        verify(patientRepository, times(1)).findById(patientId);
    }

    @Test
    void getPatientByIdShouldReturnNullWhenNotFound() {
        // Given
        Long patientId = 999L; // Assuming this patient doesn't exist

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        // When
        Patient result = patientService.getPatientById(patientId);

        // Then
        assertThat(result).isNull();
        verify(patientRepository, times(1)).findById(patientId);
    }
}
