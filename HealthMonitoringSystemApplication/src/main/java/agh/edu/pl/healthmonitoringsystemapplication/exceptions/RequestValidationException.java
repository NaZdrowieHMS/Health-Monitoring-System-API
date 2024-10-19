package agh.edu.pl.healthmonitoringsystemapplication.exceptions;


public class RequestValidationException extends RuntimeException {
    public RequestValidationException(String message) {
        super(message);
    }
}
