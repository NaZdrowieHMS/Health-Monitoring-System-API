package agh.edu.pl.healthmonitoringsystem.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Builder
@Getter
public class BatchPredictionUploadRequest {

    @NotEmpty(message = "Predictions are required")
    private List<PredictionUploadRequest> predictions;

    @JsonCreator
    public BatchPredictionUploadRequest(@JsonProperty("predictions") List<PredictionUploadRequest> predictions) {
        this.predictions = predictions;
    }
}
