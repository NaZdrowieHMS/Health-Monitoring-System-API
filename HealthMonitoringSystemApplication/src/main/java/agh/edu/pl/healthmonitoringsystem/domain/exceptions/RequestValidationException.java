package agh.edu.pl.healthmonitoringsystem.domain.exceptions;


public class RequestValidationException extends RuntimeException {
    public RequestValidationException(String message) {
        super(message);
    }
}
