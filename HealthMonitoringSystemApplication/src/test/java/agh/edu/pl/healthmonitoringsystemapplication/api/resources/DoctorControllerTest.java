package agh.edu.pl.healthmonitoringsystemapplication.api.resources;

import agh.edu.pl.healthmonitoringsystemapplication.ModelRequestTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions.RequestValidationException;
import agh.edu.pl.healthmonitoringsystemapplication.persistence.model.table.Doctor;
import agh.edu.pl.healthmonitoringsystemapplication.domain.models.request.DoctorRequest;
import agh.edu.pl.healthmonitoringsystemapplication.domain.models.response.DoctorResponse;
import agh.edu.pl.healthmonitoringsystemapplication.domain.services.DoctorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DoctorControllerTest {

    @InjectMocks
    private DoctorController doctorController;

    @Mock
    private DoctorService doctorService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(doctorController).build();
        objectMapper = new ObjectMapper();
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

    @Test
    void testCreateDoctorShouldReturn201WhenSuccessful() {
        // Given
        DoctorRequest doctorRequest = ModelRequestTestUtil.doctorRequestBuilder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("12345678901")
                .pwz("5425740")
                .build();

        Doctor createdDoctor = ModelTestUtil.doctorBuilder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("12345678901")
                .pwz("5425740")
                .build();

        when(doctorService.createDoctor(doctorRequest)).thenReturn(createdDoctor);

        // When
        ResponseEntity<DoctorResponse> response = doctorController.createDoctor(doctorRequest);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getName()).isEqualTo("John");
        assertThat(response.getBody().getSurname()).isEqualTo("Doe");
        assertThat(response.getBody().getEmail()).isEqualTo("john.doe@example.com");
        assertThat(response.getBody().getPesel()).isEqualTo("12345678901");

        verify(doctorService, times(1)).createDoctor(doctorRequest);
    }

    @Test
    void testCreateDoctorShouldReturn400WhenInvalidRequest() throws Exception {
        // Given
        DoctorRequest invalidRequest = ModelRequestTestUtil.doctorRequestBuilder()
                .name(null)
                .build();

        // When
        mockMvc.perform(post("/api/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                // Then
                .andExpect(status().isBadRequest());
        verify(doctorService, never()).createDoctor(any(DoctorRequest.class));
    }


    @Test
    void testCreateDoctorShouldReturn500WhenServiceThrowsException() {
        // Given
        DoctorRequest doctorRequest = ModelRequestTestUtil.doctorRequestBuilder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("12345678901")
                .pwz("5425740")
                .build();

        when(doctorService.createDoctor(doctorRequest)).thenThrow(new RuntimeException("Service error"));

        // When
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            doctorController.createDoctor(doctorRequest);
        });

        // Then
        assertThat(exception.getMessage()).isEqualTo("Service error");
        verify(doctorService, times(1)).createDoctor(doctorRequest);
    }
}

