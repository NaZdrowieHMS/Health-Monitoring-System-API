package agh.edu.pl.healthmonitoringsystem.domain.model.response;

import agh.edu.pl.healthmonitoringsystem.ModelTestUtil;
import agh.edu.pl.healthmonitoringsystem.domain.exception.RequestValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AiPredictionTest {

    @Test
    public void shouldInitializeFieldsCorrectly() {
        // Given
        boolean success = true;
        String predictionStatus = "malignant";
        float confidence = 0.95f;

        // When
        Prediction prediction = ModelTestUtil.predictionBuilder()
                .success(success).confidence(confidence).prediction(predictionStatus).build();

        // Then
        assertEquals(predictionStatus, prediction.getPrediction());
        assertEquals(confidence, prediction.getConfidence(), 0.001);
        assertTrue(prediction.getSuccess());
    }

    @Test
    public void builderShouldInitializeFieldsCorrectly() {
        // Given
        boolean success = false;
        String predictionStatus = "normal";
        float confidence = 0.75f;

        // When
        Prediction prediction = Prediction.builder()
                .success(success)
                .prediction(predictionStatus)
                .confidence(confidence)
                .build();

        // Then
        assertEquals(predictionStatus, prediction.getPrediction());
        assertEquals(confidence, prediction.getConfidence(), 0.001);
        assertFalse(prediction.getSuccess());
    }

    @Test
    public void constructorShouldHandleBoundaryValues() {
        // Given
        boolean success = true;
        String predictionStatus = "malignant";
        float confidence = 1.0f; // Boundary value

        // When
        Prediction prediction = ModelTestUtil.predictionBuilder()
                .success(success).confidence(confidence).prediction(predictionStatus).build();

        // Then
        assertEquals(predictionStatus, prediction.getPrediction());
        assertEquals(confidence, prediction.getConfidence(), 0.001);
        assertTrue(prediction.getSuccess());
    }

    @Test
    public void shouldHandleDefaultValues() {
        // Given
        Prediction prediction = Prediction.builder()
                .success(false)
                .prediction("unknown")
                .confidence(0.0f)
                .build();

        // When & Then
        assertFalse(prediction.getSuccess());
        assertEquals("unknown", prediction.getPrediction());
        assertEquals(0.0f, prediction.getConfidence(), 0.001);
    }

    @Test
    public void constructorShouldThrowExceptionWhenSuccessIsNull() {
        // Given
        String predictionStatus = "malignant";
        float confidence = 0.95f;

        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () -> {
            Prediction.builder()
                    .success(null)
                    .prediction(predictionStatus)
                    .confidence(confidence)
                    .build();
        });
        assertEquals("Success status cannot be null", exception.getMessage());
    }

    @Test
    public void constructorShouldThrowExceptionWhenPredictionIsNull() {
        // Given
        boolean success = true;
        float confidence = 0.95f;

        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () -> {
            Prediction.builder()
                    .success(success)
                    .confidence(confidence)
                    .build();
        });
        assertEquals("Prediction cannot be null", exception.getMessage());
    }

    @Test
    public void constructorShouldThrowExceptionWhenConfidenceIsNull() {
        // Given
        boolean success = true;
        String predictionStatus = "malignant";

        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () -> {
            Prediction.builder()
                    .success(success)
                    .confidence(null)
                    .prediction(predictionStatus)
                    .build();
        });
        assertEquals("Confidence cannot be null", exception.getMessage());
    }

    @Test
    public void constructorShouldThrowExceptionWhenConfidenceIsNegative() {
        // Given
        boolean success = true;
        String predictionStatus = "malignant";
        float confidence = -0.5f;

        // When & Then
        Exception exception = assertThrows(RequestValidationException.class, () -> {
            Prediction.builder()
                    .success(success)
                    .prediction(predictionStatus)
                    .confidence(confidence)
                    .build();
        });
        assertEquals("Confidence must be in the range from 0 to 1", exception.getMessage());
    }
}
