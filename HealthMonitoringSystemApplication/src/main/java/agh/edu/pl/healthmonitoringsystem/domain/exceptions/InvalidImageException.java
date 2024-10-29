package agh.edu.pl.healthmonitoringsystem.domain.exceptions;

public class InvalidImageException extends RuntimeException {
    public InvalidImageException(String message) {
        super(message);
    }
}