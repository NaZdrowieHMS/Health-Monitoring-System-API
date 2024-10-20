package agh.edu.pl.healthmonitoringsystemapplication.resources.predictions;

import agh.edu.pl.healthmonitoringsystemapplication.ModelResponseTestUtil;
import agh.edu.pl.healthmonitoringsystemapplication.exceptions.RequestValidationException;
import agh.edu.pl.healthmonitoringsystemapplication.resources.predictions.models.PredictionResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PredictionResponseTest {

    @Test
    public void shouldInitializeFieldsCorrectly() {
        // Given
        boolean success = true;
        String predictionStatus = "malignant";
        float confidence = 0.95f;

        // When
        PredictionResponse prediction = ModelResponseTestUtil.predictionResponseBuilder()
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
        PredictionResponse prediction = PredictionResponse.builder()
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
        PredictionResponse prediction = ModelResponseTestUtil.predictionResponseBuilder()
                .success(success).confidence(confidence).prediction(predictionStatus).build();

        // Then
        assertEquals(predictionStatus, prediction.getPrediction());
        assertEquals(confidence, prediction.getConfidence(), 0.001);
        assertTrue(prediction.getSuccess());
    }

    @Test
    public void shouldHandleDefaultValues() {
        // Given
        PredictionResponse prediction = PredictionResponse.builder()
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
            PredictionResponse.builder()
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
            PredictionResponse.builder()
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
            PredictionResponse.builder()
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
            PredictionResponse.builder()
                    .success(success)
                    .prediction(predictionStatus)
                    .confidence(confidence)
                    .build();
        });
        assertEquals("Confidence must be in the range from 0 to 1", exception.getMessage());
    }
}
