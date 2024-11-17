package agh.edu.pl.healthmonitoringsystem.domain.exception;

public class RequestValidationException extends RuntimeException {
    public RequestValidationException(String message) {
        super(message);
    }
}
