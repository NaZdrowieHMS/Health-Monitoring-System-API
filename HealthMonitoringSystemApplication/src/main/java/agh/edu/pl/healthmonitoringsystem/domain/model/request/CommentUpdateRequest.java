package agh.edu.pl.healthmonitoringsystem.domain.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentUpdateRequest {

    @NotNull(message = "Doctor Id is required")
    private Long commentId;

    @NotNull(message = "Doctor Id is required")
    private Long doctorId;

    @NotBlank(message = "Content is required")
    private String content;

    public CommentUpdateRequest(@JsonProperty("commentId") Long commentId,
                          @JsonProperty("doctorId") Long doctorId,
                          @JsonProperty("content") String content) {
        this.commentId = commentId;
        this.doctorId = doctorId;
        this.content = content;
    }
}
