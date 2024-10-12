package agh.edu.pl.healthmonitoringsystemapplication.resources.predictions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PredictionRequest {

    @NotBlank(message = "Image in Base64 format is mandatory")
    private String imageBase64;

    @JsonCreator
    public PredictionRequest(@JsonProperty("imageBase64") String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
