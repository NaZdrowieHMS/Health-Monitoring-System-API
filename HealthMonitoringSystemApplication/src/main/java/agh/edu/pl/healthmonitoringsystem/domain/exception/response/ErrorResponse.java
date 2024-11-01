package agh.edu.pl.healthmonitoringsystem.domain.exception.response;

import lombok.Data;


@Data
public class ErrorResponse {
    private int statusCode;
    private String message;

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}