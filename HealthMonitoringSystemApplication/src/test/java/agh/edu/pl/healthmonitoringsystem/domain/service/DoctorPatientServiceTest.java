package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.ModelEntityTestUtil;
import agh.edu.pl.healthmonitoringsystem.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.Patient;
import agh.edu.pl.healthmonitoringsystem.persistence.PatientRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.PatientEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DoctorPatientServiceTest {

    @InjectMocks
    private DoctorPatientService doctorPatientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPatientsByDoctorIdReturnsPatientList() {
        // Given
        Long doctorId = 1L;
        int page = 0;
        int size = 2;
        PageRequest pageRequest = PageRequest.of(page, size);

        PatientEntity patientEntity1 = ModelEntityTestUtil.patientBuilder().name("John").build();
        PatientEntity patientEntity2 = ModelEntityTestUtil.patientBuilder().name("Jane").build();
        List<PatientEntity> patientEntities = List.of(patientEntity1, patientEntity2);
        Page<PatientEntity> patientPage = new PageImpl<>(patientEntities, pageRequest, patientEntities.size());

        when(patientRepository.findPatientsByDoctorId(doctorId, pageRequest)).thenReturn(patientPage);

        Patient patient1 = ModelTestUtil.patientBuilder().name("John").build();
        Patient patient2 = ModelTestUtil.patientBuilder().name("Jane").build();
        when(modelMapper.mapPatientEntityToPatient(patientEntity1)).thenReturn(patient1);
        when(modelMapper.mapPatientEntityToPatient(patientEntity2)).thenReturn(patient2);

        // When
        List<Patient> result = doctorPatientService.getPatientsByDoctorId(doctorId, page, size);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("John");
        assertThat(result.get(1).getName()).isEqualTo("Jane");
        verify(patientRepository, times(1)).findPatientsByDoctorId(doctorId, pageRequest);
    }

    @Test
    void testGetPatientsByDoctorIdReturnsEmptyListWhenNoPatientsFound() {
        // Given
        Long doctorId = 1L;
        int page = 0;
        int size = 5;
        PageRequest pageRequest = PageRequest.of(page, size);

        when(patientRepository.findPatientsByDoctorId(doctorId, pageRequest)).thenReturn(new PageImpl<>(Collections.emptyList(), pageRequest, 0));

        // When
        List<Patient> result = doctorPatientService.getPatientsByDoctorId(doctorId, page, size);

        // Then
        assertThat(result).isEmpty();
        verify(patientRepository, times(1)).findPatientsByDoctorId(doctorId, pageRequest);
    }

    @Test
    void testGetPatientsByDoctorIdHandlesRepositoryException() {
        // Given
        Long doctorId = 1L;
        int page = 0;
        int size = 5;
        PageRequest pageRequest = PageRequest.of(page, size);

        when(patientRepository.findPatientsByDoctorId(doctorId, pageRequest)).thenThrow(new RuntimeException("Repository error"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            doctorPatientService.getPatientsByDoctorId(doctorId, page, size);
        });

        assertThat(exception.getMessage()).isEqualTo("Repository error");
        verify(patientRepository, times(1)).findPatientsByDoctorId(doctorId, pageRequest);
    }
}
