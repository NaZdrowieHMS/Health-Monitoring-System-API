package agh.edu.pl.healthmonitoringsystemapplication.resources.predictions.models;

import agh.edu.pl.healthmonitoringsystemapplication.exceptions.RequestValidationException;
import lombok.Getter;

import static agh.edu.pl.healthmonitoringsystemapplication.validators.PreconditionValidator.checkNotNull;


@Getter
public class PredictionResponse {
    private final Boolean success;
    private final String prediction;
    private final Float confidence;

    @lombok.Builder(builderClassName = "Builder")
    private PredictionResponse(Boolean success, String prediction, Float confidence) {
        this.success = success;
        this.prediction = prediction;
        this.confidence = confidence;
    }

    public static final class Builder {
        public PredictionResponse build(){
            checkNotNull(success, () -> new RequestValidationException("Success status cannot be null"));
            checkNotNull(prediction, () -> new RequestValidationException("Prediction cannot be null"));
            checkNotNull(confidence, () -> new RequestValidationException("Confidence cannot be null"));
            if (confidence < 0 || confidence > 1){
                throw new RequestValidationException("Confidence must be in the range from 0 to 1");
            }
            return new PredictionResponse(success, prediction, confidence);
        }
    }
}
