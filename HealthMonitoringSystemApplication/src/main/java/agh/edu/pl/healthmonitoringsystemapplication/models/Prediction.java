package agh.edu.pl.healthmonitoringsystemapplication.models;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class Prediction {
    private final boolean success;
    private final String prediction;
    private final float confidence;

    public Prediction(boolean success, String prediction, float confidence) {
        this.success = success;
        this.prediction = prediction;
        this.confidence = confidence;
    }
}
