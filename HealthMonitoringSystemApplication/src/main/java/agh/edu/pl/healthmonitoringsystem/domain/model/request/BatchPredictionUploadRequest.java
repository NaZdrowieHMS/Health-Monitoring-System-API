package agh.edu.pl.healthmonitoringsystem.domain.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BatchPredictionUploadRequest {

    @NotEmpty(message = "Predictions are required")
    private List<PredictionUploadRequest> predictions;

    @JsonCreator
    public BatchPredictionUploadRequest(@JsonProperty("predictions") List<PredictionUploadRequest> predictions) {
        this.predictions = predictions;
    }
}
