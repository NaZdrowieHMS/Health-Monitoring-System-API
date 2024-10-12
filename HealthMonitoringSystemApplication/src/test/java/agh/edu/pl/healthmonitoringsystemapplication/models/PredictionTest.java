package agh.edu.pl.healthmonitoringsystemapplication.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PredictionTest {

    @Test
    public void shouldInitializeFieldsCorrectly() {
        // Given
        boolean success = true;
        String predictionStatus = "malignant";
        float confidence = 0.95f;

        // When
        Prediction prediction = new Prediction(success, predictionStatus, confidence);

        // Then
        assertEquals(predictionStatus, prediction.getPrediction());
        assertEquals(confidence, prediction.getConfidence(), 0.001);
        assertTrue(prediction.isSuccess());
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
        assertFalse(prediction.isSuccess());
    }

    @Test
    public void constructorShouldHandleBoundaryValues() {
        // Given
        boolean success = true;
        String predictionStatus = "malignant";
        float confidence = 1.0f; // Boundary value

        // When
        Prediction prediction = new Prediction(success, predictionStatus, confidence);

        // Then
        assertEquals(predictionStatus, prediction.getPrediction());
        assertEquals(confidence, prediction.getConfidence(), 0.001);
        assertTrue(prediction.isSuccess());
    }

    @Test
    public void builderShouldHandleNullPrediction() {
        // When
        Prediction prediction = Prediction.builder()
                .success(true)
                .prediction(null) // Null value
                .confidence(0.0f)
                .build();

        // Then
        assertNull(prediction.getPrediction());
        assertEquals(0.0f, prediction.getConfidence(), 0.001);
        assertTrue(prediction.isSuccess());
    }

    @Test
    public void constructorShouldHandleNullPrediction() {
        // When
        Prediction prediction = new Prediction(true, null, 0.5f);

        // Then
        assertNull(prediction.getPrediction());
        assertEquals(0.5f, prediction.getConfidence(), 0.001);
        assertTrue(prediction.isSuccess());
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
        assertFalse(prediction.isSuccess());
        assertEquals("unknown", prediction.getPrediction());
        assertEquals(0.0f, prediction.getConfidence(), 0.001);
    }
}
