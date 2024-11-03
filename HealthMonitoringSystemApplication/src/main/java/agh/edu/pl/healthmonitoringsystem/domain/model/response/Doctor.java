package agh.edu.pl.healthmonitoringsystem.domain.model.response;

public record Doctor(
        Long id,
        String name,
        String surname,
        String email,
        String pesel,
        String pwz)
{}