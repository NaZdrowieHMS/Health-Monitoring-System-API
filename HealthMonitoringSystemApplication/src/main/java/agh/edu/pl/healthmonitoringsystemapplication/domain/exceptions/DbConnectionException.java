package agh.edu.pl.healthmonitoringsystemapplication.domain.exceptions;


public class DbConnectionException extends RuntimeException {
    public DbConnectionException(String message){
        super(message);
    }
}