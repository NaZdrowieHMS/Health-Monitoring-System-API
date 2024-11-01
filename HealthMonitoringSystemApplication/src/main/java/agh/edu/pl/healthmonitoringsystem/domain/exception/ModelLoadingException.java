package agh.edu.pl.healthmonitoringsystem.domain.exception;

public class ModelLoadingException extends RuntimeException {
    public ModelLoadingException(String message) {
        super(message);
    }

    public ModelLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
