package agh.edu.pl.healthmonitoringsystem.domain.model.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class FormEntryRequest {

    @NotBlank(message = "Form Id is required")
    private String healthParam;

    @NotBlank(message = "Form Id is required")
    private String value;

    public FormEntryRequest(@JsonProperty("healthParam") String healthParam,
                            @JsonProperty("value") String value) {
        this.healthParam = healthParam;
        this.value = value;
    }
}
