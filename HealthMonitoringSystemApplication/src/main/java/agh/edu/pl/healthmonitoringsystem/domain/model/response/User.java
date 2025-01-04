package agh.edu.pl.healthmonitoringsystem.domain.model.response;

import agh.edu.pl.healthmonitoringsystem.domain.model.Role;

public record User(
        Role role,
        Long id,
        String name,
        String surname,
        String email,
        String pesel,
        String password,
        String pwz) {

}
