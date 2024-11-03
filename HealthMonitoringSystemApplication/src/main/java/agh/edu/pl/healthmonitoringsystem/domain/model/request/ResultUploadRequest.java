package agh.edu.pl.healthmonitoringsystem.domain.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultUploadRequest {

    @NotNull(message = "Patient Id is required")
    private Long patientId;

    @NotBlank(message = "Test type is required")
    private String testType;

    @NotNull(message = "Content is required")
    private ResultDataRequest content;

    @Nullable
    private Long referralId;

    @JsonCreator
    public ResultUploadRequest(@JsonProperty("patientId") Long patientId,
                               @JsonProperty("testType") String testType,
                               @JsonProperty("content") ResultDataRequest content,
                               @JsonProperty("referralId") Long referralId) {
        this.patientId = patientId;
        this.testType = testType;
        this.content = content;
        this.referralId = referralId;
    }
}