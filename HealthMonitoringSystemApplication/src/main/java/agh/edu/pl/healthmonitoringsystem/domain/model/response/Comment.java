package agh.edu.pl.healthmonitoringsystem.domain.model.response;

import java.time.LocalDateTime;

public record Comment (
        Author doctorAuthor,
        LocalDateTime modifiedDate,
        String content
){}
