package agh.edu.pl.healthmonitoringsystem.domain.exceptions;

public class InvalidTensorShapeException extends RuntimeException {
    public InvalidTensorShapeException(String message) {
        super(message);
    }
}