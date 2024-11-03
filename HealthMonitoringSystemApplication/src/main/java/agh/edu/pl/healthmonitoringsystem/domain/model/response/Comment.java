package agh.edu.pl.healthmonitoringsystem.domain.model.response;

import java.time.LocalDateTime;

public record Comment (
        Author doctor,
        LocalDateTime modifiedDate,
        String content
){}
