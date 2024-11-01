package agh.edu.pl.healthmonitoringsystem.domain.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultRequest {

    @NotNull(message = "Result Id Result")
    private Long resultId;

    @NotNull(message = "Patient Id Result")
    private Long patientId;

    @NotNull(message = "Doctor Id Result")
    private Long doctorId;

    @JsonCreator
    public ResultRequest(@JsonProperty("resultId") Long resultId,
                         @JsonProperty("patientId") Long patientId,
                         @JsonProperty("doctorId") Long doctorId) {
        this.resultId = resultId;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }
}