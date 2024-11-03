package agh.edu.pl.healthmonitoringsystem.domain.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReferralRequest {

    @NotNull(message = "Doctor Id is required")
    private Long doctorId;

    @NotNull(message = "Patient Id is required")
    private Long patientId;

    @NotBlank(message = "Test type is required")
    private String testType;

    @NotBlank(message = "Referral number is required")
    private String referralNumber;

    @Nullable
    private Boolean completed;

    @Nullable
    private String comment;


    public ReferralRequest(@JsonProperty("doctorId") Long doctorId,
                           @JsonProperty("patientId") Long patientId,
                           @JsonProperty("testType") String testType,
                           @JsonProperty("referralNumber") String referralNumber,
                           @JsonProperty("completed") Boolean completed,
                           @JsonProperty("comment") String comment) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.testType = testType;
        this.referralNumber = referralNumber;
        this.completed = completed;
        this.comment = comment;
    }
}
