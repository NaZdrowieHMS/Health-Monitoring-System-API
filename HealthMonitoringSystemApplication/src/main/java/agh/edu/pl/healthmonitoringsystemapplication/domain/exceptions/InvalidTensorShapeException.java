package agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions;

public class InvalidTensorShapeException extends RuntimeException {
    public InvalidTensorShapeException(String message) {
        super(message);
    }
}