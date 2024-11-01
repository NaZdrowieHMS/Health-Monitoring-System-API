package agh.edu.pl.healthmonitoringsystem.domain.exception;

public class InvalidImageException extends RuntimeException {
    public InvalidImageException(String message) {
        super(message);
    }
}