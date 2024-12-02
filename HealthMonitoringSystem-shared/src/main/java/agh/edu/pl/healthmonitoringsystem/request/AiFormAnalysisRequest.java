package agh.edu.pl.healthmonitoringsystem.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AiFormAnalysisRequest {

    @NotNull(message = "Patient Id is required")
    private Long patientId;

    @NotNull(message = "Doctor Id is required")
    private Long doctorId;

    @NotNull(message = "Form Id is required")
    private Long formId;

    @NotNull(message = "Diagnoses are required")
    @NotEmpty(message = "Diagnoses list cannot be empty")
    private List<String> diagnoses;

    @NotNull(message = "Recommendations are required")
    @NotEmpty(message = "Recommendations list cannot be empty")
    private List<String> recommendations;

    @JsonCreator
    public AiFormAnalysisRequest(@JsonProperty("patientId") Long patientId,
                                 @JsonProperty("doctorId") Long doctorId,
                                 @JsonProperty("formId") Long formId,
                                 @JsonProperty("diagnoses") List<String> diagnoses,
                                 @JsonProperty("recommendations") List<String> recommendations) {
        this.patientId = patientId;
        this.formId = formId;
        this.diagnoses = diagnoses;
        this.recommendations = recommendations;
    }
}