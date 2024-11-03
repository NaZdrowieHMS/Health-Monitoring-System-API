package agh.edu.pl.healthmonitoringsystem.domain.exception;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message){
        super(String.format("Access denied. %s", message));
    }
}