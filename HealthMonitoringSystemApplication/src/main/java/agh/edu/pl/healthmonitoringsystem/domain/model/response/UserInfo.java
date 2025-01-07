package agh.edu.pl.healthmonitoringsystem.domain.model.response;

import agh.edu.pl.healthmonitoringsystem.domain.model.Role;

public record UserInfo(
    Role role,
    Long id,
    String jwt
)
{}
