package agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions;


public class RequestValidationException extends RuntimeException {
    public RequestValidationException(String message) {
        super(message);
    }
}
