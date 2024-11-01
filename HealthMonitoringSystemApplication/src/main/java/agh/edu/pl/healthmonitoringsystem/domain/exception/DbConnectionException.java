package agh.edu.pl.healthmonitoringsystem.domain.exception;


public class DbConnectionException extends RuntimeException {
    public DbConnectionException(String message){
        super(message);
    }
}