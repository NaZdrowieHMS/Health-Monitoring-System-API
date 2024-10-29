package agh.edu.pl.healthmonitoringsystem.domain.models.request;

import agh.edu.pl.healthmonitoringsystem.ModelRequestTestUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidPatientRequest() {
        // Given
        PatientRequest patientRequest = ModelRequestTestUtil.patientRequestBuilder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("12345678901")
                .build();

        // When
        Set<ConstraintViolation<PatientRequest>> violations = validator.validate(patientRequest);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void testInvalidNameShouldFailValidation() {
        // Given
        PatientRequest patientRequest = ModelRequestTestUtil.patientRequestBuilder()
                .name("")
                .build();

        // When
        Set<ConstraintViolation<PatientRequest>> violations = validator.validate(patientRequest);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Name is required");
    }

    @Test
    void testInvalidSurnameShouldFailValidation() {
        // Given
        PatientRequest patientRequest = ModelRequestTestUtil.patientRequestBuilder()
                .surname("")
                .build();

        // When
        Set<ConstraintViolation<PatientRequest>> violations = validator.validate(patientRequest);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Surname is required");
    }

    @Test
    void testInvalidEmailShouldFailValidation() {
        // Given
        PatientRequest patientRequest = ModelRequestTestUtil.patientRequestBuilder()
                .email("invalid-email")
                .build();

        // When
        Set<ConstraintViolation<PatientRequest>> violations = validator.validate(patientRequest);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Invalid email format");
    }

    @Test
    void testInvalidPeselShouldFailValidation() {
        // Given
        PatientRequest patientRequest = ModelRequestTestUtil.patientRequestBuilder()
                .pesel("12345678")
                .build();

        // When
        Set<ConstraintViolation<PatientRequest>> violations = validator.validate(patientRequest);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Invalid PESEL format");
    }
}

