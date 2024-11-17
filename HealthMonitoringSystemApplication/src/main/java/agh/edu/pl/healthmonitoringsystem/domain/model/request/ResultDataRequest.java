package agh.edu.pl.healthmonitoringsystem.domain.model.request;

import agh.edu.pl.healthmonitoringsystem.enums.ResultDataType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultDataRequest {

    @NotBlank(message = "Data is required")
    private String data;

    @NotBlank(message = "Data type is required")
    private ResultDataType type;

    @JsonCreator
    public ResultDataRequest(@JsonProperty("data") String data,
                             @JsonProperty("type") ResultDataType type) {
        this.data = data;
        this.type = type;
    }
}
