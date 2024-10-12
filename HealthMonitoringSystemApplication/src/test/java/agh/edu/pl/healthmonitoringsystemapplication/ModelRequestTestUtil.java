package agh.edu.pl.healthmonitoringsystemapplication;

import agh.edu.pl.healthmonitoringsystemapplication.resources.predictions.PredictionRequest;

public class ModelRequestTestUtil {
    public static PredictionRequest.PredictionRequestBuilder predictionRequestBuilder() {
        return PredictionRequest.builder()
                .imageBase64("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUA...");
    }
}
