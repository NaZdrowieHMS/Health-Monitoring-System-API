package agh.edu.pl.healthmonitoringsystem.domain.model;

import java.util.Arrays;

public enum Role {
    DOCTOR("Doctor"),
    PATIENT("Patient");

    public final String id;

    Role(String id) {
        this.id = id;
    }

    public static Role fromString(String id) {
        return Arrays.stream(Role.values())
                .filter(role -> role.id.equalsIgnoreCase(id)) // Case-insensitive comparison
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid role: " + id));
    }
}
