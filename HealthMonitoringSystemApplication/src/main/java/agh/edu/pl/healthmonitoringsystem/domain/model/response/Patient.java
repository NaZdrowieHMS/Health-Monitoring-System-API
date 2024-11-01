package agh.edu.pl.healthmonitoringsystem.domain.model.response;

public record Patient (
    Long id,
    String name,
    String surname,
    String email,
    String pesel )
{}
