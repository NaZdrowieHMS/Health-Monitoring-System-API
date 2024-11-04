package agh.edu.pl.healthmonitoringsystem.domain.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FormRequest {

    @NotNull(message = "Patient Id is required")
    private Long patientId;

    @NotEmpty(message = "Content is required")
    private List<FormEntryRequest> content;

    public FormRequest(@JsonProperty("patientId") Long patientId,
                       @JsonProperty("content") List<FormEntryRequest> content) {
        this.patientId = patientId;
        this.content = content;
    }
}