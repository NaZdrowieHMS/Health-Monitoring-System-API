package agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message){
        super(String.format("Entity not found: %s", message));
    }
}