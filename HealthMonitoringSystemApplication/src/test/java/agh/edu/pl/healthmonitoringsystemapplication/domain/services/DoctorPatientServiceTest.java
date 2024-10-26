package agh.edu.pl.healthmonitoringsystemapplication.domain.services;

import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.PatientRepository;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.table.Patient;
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
import static org.mockito.Mockito.*;

class DoctorPatientServiceTest {

    @InjectMocks
    private DoctorPatientService doctorPatientService;

    @Mock
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPatientsByDoctorIdReturnsPatientList() {
        // Given
        Long doctorId = 1L;
        int page = 0;
        int size = 5;
        PageRequest pageRequest = PageRequest.of(page, size);

        Patient patient1 = ModelTestUtil.patientBuilder().name("John").build();
        Patient patient2 = ModelTestUtil.patientBuilder().name("Jane").build();
        List<Patient> patients = List.of(patient1, patient2);
        Page<Patient> patientPage = new PageImpl<>(patients, pageRequest, patients.size());

        when(patientRepository.findPatientsByDoctorId(doctorId, pageRequest)).thenReturn(patientPage);

        // When
        List<Patient> result = doctorPatientService.getPatientsByDoctorId(doctorId, page, size);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("John");
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
    void testGetPatientsByDoctorIdWithPagination() {
        // Given
        Long doctorId = 1L;
        int page = 1;
        int size = 2;
        PageRequest pageRequest = PageRequest.of(page, size);

        Patient patient1 = ModelTestUtil.patientBuilder().name("Jane").build();
        Patient patient2 = ModelTestUtil.patientBuilder().name("John").build();
        List<Patient> patients = List.of(patient1, patient2);
        Page<Patient> patientPage = new PageImpl<>(patients, pageRequest, 10);

        when(patientRepository.findPatientsByDoctorId(doctorId, pageRequest)).thenReturn(patientPage);

        // When
        List<Patient> result = doctorPatientService.getPatientsByDoctorId(doctorId, page, size);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(1).getName()).isEqualTo("John");
        verify(patientRepository, times(1)).findPatientsByDoctorId(doctorId, pageRequest);
    }
}
