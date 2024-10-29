package agh.edu.pl.healthmonitoringsystem.domain.models.response;

import agh.edu.pl.healthmonitoringsystem.domain.exceptions.RequestValidationException;
import lombok.Getter;

import static agh.edu.pl.healthmonitoringsystem.domain.validators.PreconditionValidator.checkNotNull;


@Getter
public class Prediction {
    private final Boolean success;
    private final String prediction;
    private final Float confidence;

    @lombok.Builder(builderClassName = "Builder")
    private Prediction(Boolean success, String prediction, Float confidence) {
        this.success = success;
        this.prediction = prediction;
        this.confidence = confidence;
    }

    public static final class Builder {
        public Prediction build(){
            checkNotNull(success, () -> new RequestValidationException("Success status cannot be null"));
            checkNotNull(prediction, () -> new RequestValidationException("Prediction cannot be null"));
            checkNotNull(confidence, () -> new RequestValidationException("Confidence cannot be null"));
            if (confidence < 0 || confidence > 1){
                throw new RequestValidationException("Confidence must be in the range from 0 to 1");
            }
            return new Prediction(success, prediction, confidence);
        }
    }
}
