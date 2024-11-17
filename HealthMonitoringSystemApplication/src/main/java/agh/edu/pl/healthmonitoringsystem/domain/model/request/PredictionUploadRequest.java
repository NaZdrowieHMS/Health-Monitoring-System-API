package agh.edu.pl.healthmonitoringsystem.domain.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PredictionUploadRequest {

    @NotNull(message = "Result Id is required")
    private Long resultId;

    @NotNull(message = "Doctor Id is required")
    private Long doctorId;

    @NotNull(message = "Confidence is required")
    private Double confidence;

    @NotBlank(message = "Prediction is required")
    private String prediction;

    @JsonCreator
    public PredictionUploadRequest(@JsonProperty("resultId") Long resultId,
                                   @JsonProperty("doctorId") Long doctorId,
                                   @JsonProperty("confidence") Double confidence,
                                   @JsonProperty("prediction") String prediction) {
        this.resultId = resultId;
        this.doctorId = doctorId;
        this.confidence = confidence;
        this.prediction = prediction;
    }
}