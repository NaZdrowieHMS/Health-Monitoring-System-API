package agh.edu.pl.healthmonitoringsystem.response;

import agh.edu.pl.healthmonitoringsystem.enums.ResultDataType;

public record ResultDataContent(
    ResultDataType type,
    String data
) {}
