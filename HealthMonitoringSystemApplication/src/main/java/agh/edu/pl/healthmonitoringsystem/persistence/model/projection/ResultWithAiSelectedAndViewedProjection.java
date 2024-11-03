package agh.edu.pl.healthmonitoringsystem.persistence.model.projection;

import java.time.LocalDateTime;

public interface ResultWithAiSelectedAndViewedProjection {
    Long getId();
    String getTestType();
    String getDataType();
    String getData();
    LocalDateTime getCreatedDate();
    Boolean getAiSelected();
    Boolean getViewed();
}
