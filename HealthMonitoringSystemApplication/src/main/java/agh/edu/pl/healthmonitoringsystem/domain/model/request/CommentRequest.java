package agh.edu.pl.healthmonitoringsystem.domain.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentRequest {

    @NotNull(message = "Doctor Id is required")
    private Long doctorId;

    @NotNull(message = "Patient Id is required")
    private Long patientId;

    @NotBlank(message = "Content is required")
    private String content;

    public CommentRequest(@JsonProperty("doctorId") Long doctorId,
                           @JsonProperty("patientId") Long patientId,
                           @JsonProperty("content") String content) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.content = content;
    }
}
