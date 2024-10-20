package agh.edu.pl.healthmonitoringsystemapplication.exceptions;


public class DbConnectionException extends RuntimeException {
    public DbConnectionException(String message){
        super(message);
    }
}