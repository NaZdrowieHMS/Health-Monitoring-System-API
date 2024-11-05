package agh.edu.pl.healthmonitoringsystem.domain.model.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class FormEntryRequest {

    @NotBlank(message = "Key is required")
    private String key;

    @NotBlank(message = "value is required")
    private String value;

    public FormEntryRequest(@JsonProperty("key") String key,
                            @JsonProperty("value") String value) {
        this.key = key;
        this.value = value;
    }
}
