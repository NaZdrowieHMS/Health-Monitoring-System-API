package agh.edu.pl.healthmonitoringsystem.request;

import agh.edu.pl.healthmonitoringsystem.enums.PredictionRequestStatus;
import agh.edu.pl.healthmonitoringsystem.model.FormAiAnalysis;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class PredictionSummaryUpdateRequest {

    @NotNull(message = "Request Id is required")
    private Long requestId;

    @NotNull(message = "Status is required")
    private PredictionRequestStatus status;

    private Double confidence = null;

    private String prediction = null;

    private FormAiAnalysis formAiAnalysis = null;

    @JsonCreator
    public PredictionSummaryUpdateRequest(@JsonProperty("requestId") Long requestId,
                                   @JsonProperty("status") PredictionRequestStatus status,
                                   @JsonProperty("confidence") Double confidence,
                                   @JsonProperty("prediction") String prediction,
                                   @JsonProperty("formAiAnalysis") FormAiAnalysis formAiAnalysis) {
        this.requestId = requestId;
        this.status = status;
        this.confidence = confidence;
        this.prediction = prediction;
        this.formAiAnalysis = formAiAnalysis;
    }
}