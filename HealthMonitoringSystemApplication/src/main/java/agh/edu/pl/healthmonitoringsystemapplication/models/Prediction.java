package agh.edu.pl.healthmonitoringsystemapplication.models;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class Prediction {
    @NotNull
    private final boolean success;
    @NotNull
    private final String prediction;
    @NotNull
    private final float confidence;

    public Prediction(boolean success, String prediction, float confidence) {
        this.success = success;
        this.prediction = prediction;
        this.confidence = confidence;
    }
}
