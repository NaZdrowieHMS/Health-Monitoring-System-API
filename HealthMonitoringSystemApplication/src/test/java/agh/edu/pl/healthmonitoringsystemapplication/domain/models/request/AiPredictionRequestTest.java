package agh.edu.pl.healthmonitoringsystemapplication.domain.models.request;

import agh.edu.pl.healthmonitoringsystemapplication.ModelRequestTestUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AiPredictionRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidPredictionRequest() {
        // Given
        PredictionRequest request = ModelRequestTestUtil.predictionRequestBuilder().build();

        // When
        Set<ConstraintViolation<PredictionRequest>> violations = validator.validate(request);

        // Then
        assertTrue(violations.isEmpty(), "Validation should pass for valid PredictionRequest");
    }

    @Test
    void testInvalidPredictionRequestWIthNullImageBase64() {
        // Given
        PredictionRequest request = ModelRequestTestUtil.predictionRequestBuilder().imageBase64(null).build();

        // When
        Set<ConstraintViolation<PredictionRequest>> violations = validator.validate(request);

        // Then
        assertEquals(1, violations.size(), "Validation should fail for null imageBase64");
        assertEquals("Image in Base64 format is required", violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidPredictionRequestWithEmptyImageBase64() {
        // Given
        PredictionRequest request = ModelRequestTestUtil.predictionRequestBuilder().imageBase64("").build();

        // When
        Set<ConstraintViolation<PredictionRequest>> violations = validator.validate(request);

        // Then
        assertEquals(1, violations.size(), "Validation should fail for empty imageBase64");
        assertEquals("Image in Base64 format is required", violations.iterator().next().getMessage());
    }
}
