package agh.edu.pl.healthmonitoringsystemapplication.services;

import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.exceptions.EntityNotFoundException;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        Patient patient = ModelTestUtil.patientBuilder().name("John").build();

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
        Patient patient = ModelTestUtil.patientBuilder().name("John").build();

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
    void getPatientByIdShouldThrowEntityNotFoundExceptionWhenNotFound() {
        // Given
        Long patientId = 999L; // Assuming this patient doesn't exist

        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> patientService.getPatientById(patientId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Entity not found: Patient with id " + patientId + " not found");

        verify(patientRepository, times(1)).findById(patientId);
    }
}
