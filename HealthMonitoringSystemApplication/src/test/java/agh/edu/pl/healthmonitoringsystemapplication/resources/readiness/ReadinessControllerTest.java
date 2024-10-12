package agh.edu.pl.healthmonitoringsystemapplication.resources.readiness;

import agh.edu.pl.healthmonitoringsystemapplication.exceptions.response.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class ReadinessControllerTest {

    @InjectMocks
    private ReadinessController readinessController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShouldReturn200WhenSystemIsReady() {
        // Given - here should be mocked check for db connection

        // When
        ResponseEntity<String> response = readinessController.readinessCheck();

        // Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Ready");
    }

    @Test
    void testShouldReturn500WhenSystemIsNotReady() {
        // Given - here should be mocked invalid connection to the base

        // When
        // ResponseEntity<ErrorResponse> response = readinessController.readinessCheck();

        // Then
        // assertThat(response.getStatusCodeValue()).isEqualTo(500);
        // assertThat(response.getBody()).isNotNull();
        // assertThat(response.getBody().getMessage()).isEqualTo("Not ready");
    }
}
