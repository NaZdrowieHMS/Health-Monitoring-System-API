package agh.edu.pl.healthmonitoringsystemapplication.resources.readiness;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class ReadinessCheckTest {

    @InjectMocks
    private ReadinessCheck readinessCheck;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckIfReadyShouldReturnTrueWhenDatabaseIsAccessible() {
        // Given
        when(jdbcTemplate.queryForObject("SELECT 1", Integer.class)).thenReturn(1);

        // When
        boolean result = readinessCheck.checkIfReady();

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void testCheckIfReadyShouldReturnFalseWhenDatabaseIsNotAccessible() {
        // Given
        doThrow(new RuntimeException("Database not reachable")).when(jdbcTemplate).queryForObject("SELECT 1", Integer.class);

        // When
        boolean result = readinessCheck.checkIfReady();

        // Then
        assertThat(result).isFalse();
    }
}
