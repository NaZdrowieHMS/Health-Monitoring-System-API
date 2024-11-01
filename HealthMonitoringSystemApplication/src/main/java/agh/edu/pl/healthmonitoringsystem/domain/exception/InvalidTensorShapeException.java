package agh.edu.pl.healthmonitoringsystem.domain.exception;

public class InvalidTensorShapeException extends RuntimeException {
    public InvalidTensorShapeException(String message) {
        super(message);
    }
}