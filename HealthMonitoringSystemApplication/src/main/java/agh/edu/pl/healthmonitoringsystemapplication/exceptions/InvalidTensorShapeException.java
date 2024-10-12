package agh.edu.pl.healthmonitoringsystemapplication.exceptions;

public class InvalidTensorShapeException extends RuntimeException {
    public InvalidTensorShapeException(String message) {
        super(message);
    }
}