package agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions;

public class InvalidImageException extends RuntimeException {
    public InvalidImageException(String message) {
        super(message);
    }
}