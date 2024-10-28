package agh.edu.pl.healthmonitoringsystem.domain.exceptions;


public class DbConnectionException extends RuntimeException {
    public DbConnectionException(String message){
        super(message);
    }
}