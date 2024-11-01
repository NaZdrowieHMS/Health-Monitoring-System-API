package agh.edu.pl.healthmonitoringsystem.domain.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message){
        super(String.format("Entity not found: %s", message));
    }
}