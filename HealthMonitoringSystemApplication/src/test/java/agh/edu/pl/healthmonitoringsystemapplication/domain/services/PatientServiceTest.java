package agh.edu.pl.healthmonitoringsystemapplication.domain.services;

import agh.edu.pl.healthmonitoringsystemapplication.ModelRequestTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.table.Patient;
import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.PatientRepository;
import agh.edu.pl.healthmonitoringsystemapplication.domain.models.request.PatientRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
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

    @Test
    void createPatientShouldSavePatientAndReturnIt() {
        // Given
        PatientRequest patientRequest = ModelRequestTestUtil.patientRequestBuilder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("12345678901")
                .build();

        Patient savedPatient = ModelTestUtil.patientBuilder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("12345678901")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();

        when(patientRepository.save(any(Patient.class))).thenReturn(savedPatient);

        // When
        Patient result = patientService.createPatient(patientRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John");
        assertThat(result.getSurname()).isEqualTo("Doe");
        assertThat(result.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(result.getPesel()).isEqualTo("12345678901");
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void createPatientShouldThrowExceptionWhenInvalidData() {
        // Given
        PatientRequest patientRequest = ModelRequestTestUtil.patientRequestBuilder()
                .name(null)
                .build();

        // When & Then
        assertThatThrownBy(() -> patientService.createPatient(patientRequest))
                .isInstanceOf(RequestValidationException.class)
                .hasMessageContaining("Name cannot be null");

        verify(patientRepository, never()).save(any(Patient.class));
    }
}
