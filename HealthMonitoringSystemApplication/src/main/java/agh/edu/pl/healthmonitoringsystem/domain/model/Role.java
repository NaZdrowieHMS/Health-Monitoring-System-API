package agh.edu.pl.healthmonitoringsystem.domain.model;

public enum Role {
    DOCTOR("Doctor"),
    PATIENT("Patient");

    public final String id;

    Role(String id) {
        this.id = id;
    }
}
