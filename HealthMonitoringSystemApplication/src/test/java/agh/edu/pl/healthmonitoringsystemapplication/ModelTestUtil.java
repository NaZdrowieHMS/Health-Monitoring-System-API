package agh.edu.pl.healthmonitoringsystemapplication;

import agh.edu.pl.healthmonitoringsystemapplication.models.Prediction;

public class ModelTestUtil {
    public static Prediction.PredictionBuilder predictionBuilder() {
        return Prediction.builder()
                .success(true)
                .prediction("benign")
                .confidence(0.87F);
    }
}

