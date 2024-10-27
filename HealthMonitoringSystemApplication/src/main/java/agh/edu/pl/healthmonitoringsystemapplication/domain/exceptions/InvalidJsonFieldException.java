package agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions;

public class InvalidJsonFieldException extends RuntimeException {
    public InvalidJsonFieldException(String message) {
        super(message);
    }
}
