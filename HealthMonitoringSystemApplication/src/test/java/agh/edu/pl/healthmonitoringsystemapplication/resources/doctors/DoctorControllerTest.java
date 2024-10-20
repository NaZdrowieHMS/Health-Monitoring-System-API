package agh.edu.pl.healthmonitoringsystemapplication.resources.doctors;

import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.exceptions.RequestValidationException;
import agh.edu.pl.healthmonitoringsystemapplication.databaseModels.Doctor;
import agh.edu.pl.healthmonitoringsystemapplication.resources.doctors.models.DoctorResponse;
import agh.edu.pl.healthmonitoringsystemapplication.services.DoctorService;
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

public class DoctorControllerTest {

    @InjectMocks
    private DoctorController doctorController;

    @Mock
    private DoctorService doctorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDoctorsShouldReturnListOfDoctors() {
        // Given
        Doctor doctor1 = ModelTestUtil.doctorBuilder().name("John").build();
        Doctor doctor2 = ModelTestUtil.doctorBuilder().name("Jane").build();
        List<Doctor> doctors = Arrays.asList(doctor1, doctor2);

        when(doctorService.getDoctors(0, 50)).thenReturn(doctors);

        // When
        ResponseEntity<List<DoctorResponse>> response = doctorController.getDoctors(0, 50);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).getName()).isEqualTo("John");
        assertThat(response.getBody().get(1).getName()).isEqualTo("Jane");
    }

    @Test
    void testGetDoctorsShouldReturnEmptyListWhenNoDoctorsFound() {
        // Given
        when(doctorService.getDoctors(0, 50)).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<List<DoctorResponse>> response = doctorController.getDoctors(0, 50);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    void testGetDoctorByIdShouldReturnDoctorResponse() {
        // Given
        Long doctorId = 1L;
        Doctor doctor = ModelTestUtil.doctorBuilder().name("John").build();

        when(doctorService.getDoctorById(doctorId)).thenReturn(doctor);

        // When
        ResponseEntity<DoctorResponse> response = doctorController.getDoctorById(doctorId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(doctorId);
        assertThat(response.getBody().getName()).isEqualTo("John");
    }

    @Test
    void testGetDoctorByIdShouldReturn404WhenDoctorNotFound() {
        // Given
        Long doctorId = 999L;
        when(doctorService.getDoctorById(doctorId)).thenThrow(new RequestValidationException("Doctor not found"));

        // When
        RequestValidationException exception = assertThrows(RequestValidationException.class, () -> {
            doctorController.getDoctorById(doctorId);
        });
    }
}

