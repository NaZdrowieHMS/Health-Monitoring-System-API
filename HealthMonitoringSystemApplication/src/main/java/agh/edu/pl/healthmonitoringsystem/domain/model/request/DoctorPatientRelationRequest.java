package agh.edu.pl.healthmonitoringsystem.domain.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorPatientRelationRequest {

    @NotNull(message = "Doctor Id is required")
    private Long doctorId;

    @NotNull(message = "Patient Id is required")
    private Long patientId;

    @JsonCreator
    public DoctorPatientRelationRequest(@JsonProperty("doctorId") Long doctorId,
                                        @JsonProperty("patientId") Long patientId) {
        this.doctorId = doctorId;
        this.patientId = patientId;
    }
}