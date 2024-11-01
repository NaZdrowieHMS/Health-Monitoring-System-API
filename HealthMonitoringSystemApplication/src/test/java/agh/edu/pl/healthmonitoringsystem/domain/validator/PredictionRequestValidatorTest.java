package agh.edu.pl.healthmonitoringsystem.domain.validator;

import agh.edu.pl.healthmonitoringsystem.ModelRequestTestUtil;
import agh.edu.pl.healthmonitoringsystem.domain.exception.InvalidImageException;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.PredictionRequest;
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
