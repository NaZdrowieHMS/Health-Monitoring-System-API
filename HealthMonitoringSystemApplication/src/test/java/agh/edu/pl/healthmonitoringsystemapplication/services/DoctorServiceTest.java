package agh.edu.pl.healthmonitoringsystemapplication.services;

import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.exceptions.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystemapplication.models.Doctor;
import agh.edu.pl.healthmonitoringsystemapplication.repositories.DoctorRepository;
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
}

