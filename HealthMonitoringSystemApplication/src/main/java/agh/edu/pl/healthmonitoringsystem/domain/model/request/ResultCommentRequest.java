package agh.edu.pl.healthmonitoringsystem.domain.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultCommentRequest {

    @NotNull(message = "result Id is required")
    private Long resultId;

    @NotNull(message = "Doctor Id is required")
    private Long doctorId;

    @NotBlank(message = "Content is required")
    private String content;

    public ResultCommentRequest(@JsonProperty("resultId") Long resultId,
                                @JsonProperty("doctorId") Long doctorId,
                                @JsonProperty("content") String content) {
        this.resultId = resultId;
        this.doctorId = doctorId;
        this.content = content;
    }
}