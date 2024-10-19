package agh.edu.pl.healthmonitoringsystemapplication.resources.readiness;

import agh.edu.pl.healthmonitoringsystemapplication.exceptions.response.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ReadinessControllerTest {

    @InjectMocks
    private ReadinessController readinessController;

    @Mock
    private ReadinessCheck readinessCheck;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShouldReturn200WhenSystemIsReady() {
        // Given
        when(readinessCheck.checkIfReady()).thenReturn(true);

        // When
        ResponseEntity<ErrorResponse> response = readinessController.readinessCheck();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Ready :)");
        assertThat(response.getBody().getStatusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testShouldReturn500WhenSystemIsNotReady() {
        // Given
        when(readinessCheck.checkIfReady()).thenReturn(false);

        // When
        ResponseEntity<ErrorResponse> response = readinessController.readinessCheck();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("System is not ready.");
        assertThat(response.getBody().getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
