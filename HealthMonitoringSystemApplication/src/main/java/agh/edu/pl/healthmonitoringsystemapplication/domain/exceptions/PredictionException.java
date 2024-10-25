package agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions;

public class PredictionException extends RuntimeException {
    public PredictionException(String message) {
        super(message);
    }

    public PredictionException(String message, Throwable cause) {
        super(message, cause);
    }
}