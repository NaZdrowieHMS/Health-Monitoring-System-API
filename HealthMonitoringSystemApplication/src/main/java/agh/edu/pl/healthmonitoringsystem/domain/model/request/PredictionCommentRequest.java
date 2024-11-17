package agh.edu.pl.healthmonitoringsystem.domain.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class PredictionCommentRequest {

    @NotNull(message = "Prediction (Summary) Id is required")
    private Long predictionId;

    @NotNull(message = "Doctor Id is required")
    private Long doctorId;

    @NotBlank(message = "Content is required")
    private String content;

    public PredictionCommentRequest(@JsonProperty("predictionId") Long predictionId,
                                @JsonProperty("doctorId") Long doctorId,
                                @JsonProperty("content") String content) {
        this.predictionId = predictionId;
        this.doctorId = doctorId;
        this.content = content;
    }
}