package agh.edu.pl.healthmonitoringsystemapplication.validators;

import agh.edu.pl.healthmonitoringsystemapplication.ModelRequestTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.exceptions.InvalidImageException;
import agh.edu.pl.healthmonitoringsystemapplication.resources.predictions.models.PredictionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PredictionRequestValidatorTest {

    private PredictionRequestValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PredictionRequestValidator();
    }

    @Test
    void testValidateShouldThrowInvalidImageExceptionWhenImageBase64IsNull() {
        // Given
        PredictionRequest request = ModelRequestTestUtil.predictionRequestBuilder().imageBase64(null).build();

        // When & Then
        assertThrows(InvalidImageException.class, () -> validator.validate(request), "No image provided");
    }

    @Test
    void testValidateShouldThrowInvalidImageExceptionWhenImageBase64IsEmpty() {
        // Given
        PredictionRequest request = ModelRequestTestUtil.predictionRequestBuilder().imageBase64("").build();

        // When & Then
        assertThrows(InvalidImageException.class, () -> validator.validate(request), "No image provided");
    }

    @Test
    void testValidateShouldNotThrowExceptionWhenImageBase64IsProvided() {
        // Given
        PredictionRequest request = ModelRequestTestUtil.predictionRequestBuilder().build();

        // When & Then
        validator.validate(request);
    }
}
