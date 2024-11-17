package agh.edu.pl.healthmonitoringsystem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PredictionRequest {

    @NotNull(message = "Doctor Id is required")
    private Long doctorId;

    @NotNull(message = "Patient Id is required")
    private Long patientId;

    @NotEmpty(message = "List of medical result IDs is required")
    private List<Long> resultIds;

    public PredictionRequest(@JsonProperty("doctorId") Long doctorId,
                             @JsonProperty("patientId") Long patientId,
                             @JsonProperty("resultIds") List<Long> resultIds) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.resultIds = resultIds;
    }
}

