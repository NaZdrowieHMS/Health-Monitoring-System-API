package agh.edu.pl.healthmonitoringsystem.domain.exceptions;

public class InvalidJsonFieldException extends RuntimeException {
    public InvalidJsonFieldException(String message) {
        super(message);
    }
}
