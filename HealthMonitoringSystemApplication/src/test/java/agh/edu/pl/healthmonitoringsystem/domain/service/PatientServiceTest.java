package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.ModelRequestTestUtil;
import agh.edu.pl.healthmonitoringsystem.ModelEntityTestUtil;
import agh.edu.pl.healthmonitoringsystem.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.exception.RequestValidationException;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Patient;
import agh.edu.pl.healthmonitoringsystem.persistence.PatientRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.PatientEntity;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.PatientRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
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
    private ModelMapper modelMapper;

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
        PatientEntity patientEntity = ModelEntityTestUtil.patientBuilder().name("John").build();
        Patient patient = ModelTestUtil.patientBuilder().name("John").build();

        when(patientRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.singletonList(patientEntity), pageable, 1));
        when(modelMapper.mapPatientEntityToPatient(patientEntity)).thenReturn(patient);

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

        when(patientRepository.findAll(pageable)).thenReturn(new PageImpl<>(Collections.emptyList(), pageable, 0));

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
        PatientEntity patientEntity = ModelEntityTestUtil.patientBuilder().name("John").build();
        Patient patient = ModelTestUtil.patientBuilder().name("John").build();

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patientEntity));
        when(modelMapper.mapPatientEntityToPatient(patientEntity)).thenReturn(patient);

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

        PatientEntity savedPatientEntity = ModelEntityTestUtil.patientBuilder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("12345678901")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();

        Patient patient = ModelTestUtil.patientBuilder().id(1L)
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("12345678901")
                .build();

        when(patientRepository.save(any(PatientEntity.class))).thenReturn(savedPatientEntity);
        when(modelMapper.mapPatientEntityToPatient(savedPatientEntity)).thenReturn(patient);

        // When
        Patient result = patientService.createPatient(patientRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John");
        assertThat(result.getSurname()).isEqualTo("Doe");
        assertThat(result.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(result.getPesel()).isEqualTo("12345678901");
        verify(patientRepository, times(1)).save(any(PatientEntity.class));
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

        verify(patientRepository, never()).save(any(PatientEntity.class));
    }
}
