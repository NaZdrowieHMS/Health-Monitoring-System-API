package agh.edu.pl.healthmonitoringsystem.domain.exception;

public class InvalidJsonFieldException extends RuntimeException {
    public InvalidJsonFieldException(String message) {
        super(message);
    }
}
