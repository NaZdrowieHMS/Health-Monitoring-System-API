package agh.edu.pl.healthmonitoringsystemapplication;

import agh.edu.pl.healthmonitoringsystemapplication.resources.predictions.PredictionRequest;

public class ModelRequestTestUtil {
    public static PredictionRequest.PredictionRequestBuilder predictionBuilder() {
        return PredictionRequest.builder()
                .imageBase64("dGVzdA=="); // Example (not codded image)
    }
}
