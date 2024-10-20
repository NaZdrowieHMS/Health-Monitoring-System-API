package agh.edu.pl.healthmonitoringsystemapplication.services;

import agh.edu.pl.healthmonitoringsystemapplication.ModelRequestTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.exceptions.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystemapplication.databaseModels.Doctor;
import agh.edu.pl.healthmonitoringsystemapplication.exceptions.RequestValidationException;
import agh.edu.pl.healthmonitoringsystemapplication.repositories.DoctorRepository;
import agh.edu.pl.healthmonitoringsystemapplication.resources.doctors.models.DoctorRequest;
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

class DoctorServiceTest {

    @InjectMocks
    private DoctorService doctorService;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private Page<Doctor> doctorPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDoctorShouldReturnListOfDoctors() {
        // Given
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Doctor doctor = ModelTestUtil.doctorBuilder().name("John").build();

        when(doctorRepository.findAll(pageable)).thenReturn(doctorPage);
        when(doctorPage.getContent()).thenReturn(Collections.singletonList(doctor));

        // When
        List<Doctor> doctors = doctorService.getDoctors(page, size);

        // Then
        assertThat(doctors).hasSize(1);
        assertThat(doctors.get(0).getName()).isEqualTo("John");
        verify(doctorRepository, times(1)).findAll(pageable);
    }

    @Test
    void getDoctorsShouldReturnEmptyListWhenNoDoctorsFound() {
        // Given
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        when(doctorRepository.findAll(pageable)).thenReturn(doctorPage);
        when(doctorPage.getContent()).thenReturn(Collections.emptyList());

        // When
        List<Doctor> doctors = doctorService.getDoctors(page, size);

        // Then
        assertThat(doctors).isEmpty();
        verify(doctorRepository, times(1)).findAll(pageable);
    }

    @Test
    void getDoctorByIdShouldReturnDoctorWhenFound() {
        // Given
        Long doctorId = 1L;
        Doctor doctor = ModelTestUtil.doctorBuilder().name("John").build();

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));

        // When
        Doctor result = doctorService.getDoctorById(doctorId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(doctorId);
        assertThat(result.getName()).isEqualTo("John");
        verify(doctorRepository, times(1)).findById(doctorId);
    }

    @Test
    void getDoctorByIdShouldThrowEntityNotFoundExceptionWhenNotFound() {
        // Given
        Long doctorId = 999L; // Assuming this doctor doesn't exist

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> doctorService.getDoctorById(doctorId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Entity not found: Doctor with id " + doctorId + " not found");

        verify(doctorRepository, times(1)).findById(doctorId);
    }

    @Test
    void createDoctorShouldSaveDoctorAndReturnIt() {
        // Given
        DoctorRequest doctorRequest = ModelRequestTestUtil.doctorRequestBuilder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("12345678901")
                .pwz("5425740")
                .build();

        Doctor savedDoctor = ModelTestUtil.doctorBuilder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("12345678901")
                .pwz("5425740")
                .createdAt(LocalDateTime.now())
                .build();

        when(doctorRepository.save(any(Doctor.class))).thenReturn(savedDoctor);

        // When
        Doctor result = doctorService.createDoctor(doctorRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John");
        assertThat(result.getSurname()).isEqualTo("Doe");
        assertThat(result.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(result.getPesel()).isEqualTo("12345678901");
        assertThat(result.getPwz()).isEqualTo("5425740");
        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }

    @Test
    void createDoctorShouldThrowExceptionWhenInvalidData() {
        // Given
        DoctorRequest doctorRequest = ModelRequestTestUtil.doctorRequestBuilder()
                .name(null)
                .build();

        // When & Then
        assertThatThrownBy(() -> doctorService.createDoctor(doctorRequest))
                .isInstanceOf(RequestValidationException.class)
                .hasMessageContaining("Name cannot be null");

        verify(doctorRepository, never()).save(any(Doctor.class));
    }
}

