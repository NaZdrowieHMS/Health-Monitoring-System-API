package agh.edu.pl.healthmonitoringsystem.domain.exception;


public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}