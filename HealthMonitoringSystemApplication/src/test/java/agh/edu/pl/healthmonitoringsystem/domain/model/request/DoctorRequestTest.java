package agh.edu.pl.healthmonitoringsystem.domain.model.request;

import agh.edu.pl.healthmonitoringsystem.ModelRequestTestUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class DoctorRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidDoctorRequest() {
        // Given
        DoctorRequest doctorRequest = ModelRequestTestUtil.doctorRequestBuilder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .pesel("12345678901")
                .pwz("1234567")
                .build();

        // When
        Set<ConstraintViolation<DoctorRequest>> violations = validator.validate(doctorRequest);

        // Then
        assertThat(violations).isEmpty();
    }

    @Test
    void testInvalidNameShouldFailValidation() {
        // Given
        DoctorRequest doctorRequest = ModelRequestTestUtil.doctorRequestBuilder()
                .name("")
                .build();

        // When
        Set<ConstraintViolation<DoctorRequest>> violations = validator.validate(doctorRequest);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Name is required");
    }

    @Test
    void testInvalidSurnameShouldFailValidation() {
        // Given
        DoctorRequest doctorRequest = ModelRequestTestUtil.doctorRequestBuilder()
                .surname("")
                .build();

        // When
        Set<ConstraintViolation<DoctorRequest>> violations = validator.validate(doctorRequest);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Surname is required");
    }

    @Test
    void testInvalidEmailShouldFailValidation() {
        // Given
        DoctorRequest doctorRequest = ModelRequestTestUtil.doctorRequestBuilder()
                .email("invalid-email")
                .build();

        // When
        Set<ConstraintViolation<DoctorRequest>> violations = validator.validate(doctorRequest);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Invalid email format");
    }

    @Test
    void testInvalidPeselShouldFailValidation() {
        // Given
        DoctorRequest doctorRequest = ModelRequestTestUtil.doctorRequestBuilder()
                .pesel("12345678")
                .build();

        // When
        Set<ConstraintViolation<DoctorRequest>> violations = validator.validate(doctorRequest);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Invalid PESEL format");
    }

    @Test
    void testInvalidPwzShouldFailValidation() {
        // Given
        DoctorRequest doctorRequest = ModelRequestTestUtil.doctorRequestBuilder()
                .pwz("123456")
                .build();

        // When
        Set<ConstraintViolation<DoctorRequest>> violations = validator.validate(doctorRequest);

        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Invalid PWZ format");
    }
}

