package agh.edu.pl.healthmonitoringsystem.response;

public record ResultDataContent(
    ResultDataType type,
    String data
) {}
