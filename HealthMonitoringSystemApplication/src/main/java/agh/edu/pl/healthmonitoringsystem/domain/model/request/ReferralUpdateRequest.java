package agh.edu.pl.healthmonitoringsystem.domain.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReferralUpdateRequest {

    @NotNull(message = "Referral Id is required")
    private Long referralId;

    @NotNull(message = "Doctor Id is required")
    private Long doctorId;

    @Nullable
    private String testType;

    @Nullable
    private Boolean completed;

    @Nullable
    private String comment;

    public ReferralUpdateRequest(@JsonProperty("referralId") Long referralId,
                                 @JsonProperty("doctorId") Long doctorId,
                                 @JsonProperty("testType") String testType,
                                 @JsonProperty("completed") Boolean completed,
                                 @JsonProperty("comment") String comment) {
        this.referralId = referralId;
        this.doctorId = doctorId;
        this.testType = testType;
        this.completed = completed;
        this.comment = comment;
    }
}
