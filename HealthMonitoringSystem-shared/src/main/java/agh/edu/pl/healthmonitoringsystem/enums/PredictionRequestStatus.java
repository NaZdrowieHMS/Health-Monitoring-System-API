package agh.edu.pl.healthmonitoringsystem.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PredictionRequestStatus {
    IN_PROGRESS("IN PROGRESS"),
    COMPLETED("COMPLETED"),
    FAILED("FAILED");

    public final String value;

    PredictionRequestStatus(String value) {
        this.value = value;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }

    @JsonCreator
    public static PredictionRequestStatus fromString(String value) {
        for (PredictionRequestStatus type : PredictionRequestStatus.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown prediction result status: " + value);
    }
}
