package agh.edu.pl.healthmonitoringsystem.domain.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteRequest {

    @NotNull(message = "Entity Id is required")
    private Long id;

    public DeleteRequest(@JsonProperty("id") Long id) {
        this.id = id;
    }
}
